package com.kosarict.controller;

import com.kosarict.dao.*;
import com.kosarict.entity.*;
import com.kosarict.model.Constant;
import com.kosarict.tools.PersianCalendar;
import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Sadegh-Pc on 11/30/2016.
 */
@Controller
public class WSMobile {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TabDao tabDao;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private TicketStatusDao ticketStatusDao;

    @Autowired
    private TicketErrandDao ticketErrandDao;

    @Autowired
    private TicketAttachmentDao ticketAttachmentDao;

    @Autowired
    private ComplainantRelationDao complainantRelationDao;

    @Autowired
    private HospitalSectionDao hospitalSectionDao;

    @Autowired
    private UserSectionDao userSectionDao;


    @RequestMapping(value = "/ws/api/activeUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String activeUser(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String personalNumber = URLDecoder.decode(parameter.get("PersonalNumber").get(0), "UTF-8");
            String macAddress = URLDecoder.decode(parameter.get("MacAddress").get(0), "UTF-8");

            Users user = userDao.findUserByPersonalNumber(personalNumber);

            user.setMacAddress(macAddress);

            userDao.saveUser(user);

            jsonObject.put("status", "ok");
            jsonObject.put("description", "ok");

            jsonArray.put(jsonObject);

            return jsonArray.toString();

        } catch (Exception ex) {
            jsonObject.put("status", "error");
            jsonObject.put("description", ex.toString());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        }
    }

    @RequestMapping(value = "/ws/api/getUserLastState", method = RequestMethod.POST)
    public
    @ResponseBody
    String getUserLastState(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String macAddress = URLDecoder.decode(parameter.get("MacAddress").get(0), "UTF-8");

            Users user = userDao.findUserByMacAddress(macAddress);

            jsonObject.put("status", "ok");
            jsonObject.put("description", "ok");
            jsonObject.put("isEnable", user.getLocked());

            jsonArray.put(jsonObject);

            return jsonArray.toString();

        } catch (Exception ex) {
            jsonObject.put("status", "error");
            jsonObject.put("description", ex.toString());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        }
    }

    @RequestMapping(value = "/ws/api/checkUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String loginUser(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String userName = URLDecoder.decode(parameter.get("User").get(0), "UTF-8");
            String password = URLDecoder.decode(parameter.get("Password").get(0), "UTF-8");
            String macAddress = URLDecoder.decode(parameter.get("MacAddress").get(0), "UTF-8");

            Users user = userDao.findUserByUserName(userName);

            List<Tab> tabList = tabDao.getAllMobileTabListByUserId(user.getUserId());

            JSONArray tabArray = new JSONArray();

            if (user != null) {
                if (user.getPassword().matches(password) && user.getLocked() == 0 && macAddress.matches(user.getMacAddress())) {
                    jsonObject.put("status", "ok");
                    jsonObject.put("description", "ok");
                    jsonObject.put("userServerId", user.getUserId());

                    for (Tab tab : tabList) {
                        JSONObject dataObject = new JSONObject();

                        int countOfNew = tabDao.getNumberOfNew(tab.getTabId(), user.getUserId());

                        dataObject.put("tabId", tab.getTabId());
                        dataObject.put("title", URLEncoder.encode(tab.getTitle()));
                        dataObject.put("url", tab.getUrl());
                        dataObject.put("count", countOfNew);

                        tabArray.put(dataObject);
                    }

                    jsonObject.put("tabList", tabArray);

                } else if (user.getPassword().matches(password)) {
                    jsonObject.put("status", "disable");
                    jsonObject.put("description", "wrong password");
                } else {
                    jsonObject.put("status", "disable");
                    jsonObject.put("description", "disable");
                }

                jsonArray.put(jsonObject);

                return jsonArray.toString();
            } else {
                jsonObject.put("status", "404");
                jsonObject.put("description", "User not exists");

                jsonArray.put(jsonObject);

                return jsonArray.toString();
            }

        } catch (Exception ex) {
            jsonObject.put("status", "error");
            jsonObject.put("description", ex.toString());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        }
    }

    @RequestMapping(value = "/ws/api/refreshData", method = RequestMethod.GET)
    public
    @ResponseBody
    String refreshData(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String ticketTypeId = URLDecoder.decode(parameter.get("TicketTypeId").get(0), "UTF-8");
            String macAddress = URLDecoder.decode(parameter.get("MacAddress").get(0), "UTF-8");

            Users user = userDao.findUserByMacAddress(macAddress);

            if (checkUserSessionIsActive(user)) {
                if (checkUserMacAddress(user, macAddress)) {
                    List<Ticket> tickets = ticketDao.getTicketListByTicketTypeId(Short.parseShort(ticketTypeId), user.getUserId());

                    jsonObject.put("status", "ok");
                    jsonObject.put("tickets", tickets);
                    jsonArray.put(jsonObject);

                    return jsonArray.toString();
                } else {
                    jsonObject.put("status", "101");
                    jsonObject.put("description", "Mac address not equal");

                    jsonArray.put(jsonObject);

                    return jsonArray.toString();
                }
            } else {
                jsonObject.put("status", "100");
                jsonObject.put("description", "Session failed");

                jsonArray.put(jsonObject);

                return jsonArray.toString();
            }
        } catch (Exception ex) {
            jsonObject.put("status", "error");
            jsonObject.put("description", ex.toString());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        }
    }

    private boolean checkUserSessionIsActive(Users user) {
        try {
            Calendar calendar = Calendar.getInstance();

            if (user.getLastRequestFromMobile() == null) {
                user.setLastRequestFromMobile(new Timestamp(calendar.getTimeInMillis()));

                userDao.saveUser(user);

                return true;
            } else {
                Timestamp currentTimestamp = new Timestamp(calendar.getTimeInMillis());
                Timestamp lastRequestTimestamp = new Timestamp(user.getLastRequestFromMobile().getTime());

                calendar.add(Calendar.MINUTE, 10);

                lastRequestTimestamp.setTime(calendar.getTimeInMillis());


                if (!currentTimestamp.after(lastRequestTimestamp)) {
                    user.setLastRequestFromMobile(new Timestamp(calendar.getTimeInMillis()));

                    userDao.saveUser(user);

                    return true;
                } else {
                    return false;
                }
            }


        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkUserMacAddress(Users user, String macAddress) {
        try {
            if (user.getMacAddress().matches(macAddress))
                return true;

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @RequestMapping(value = "ws/api/getTicketList", method = RequestMethod.POST)
    public
    @ResponseBody
    String getTicketList(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();


        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String ticketTypeId = URLDecoder.decode(parameter.get("TicketTypeId").get(0), "UTF-8");
            String macAddress = URLDecoder.decode(parameter.get("MacAddress").get(0), "UTF-8");

            Users user = userDao.findUserByMacAddress(macAddress);


            if (checkUserSessionIsActive(user)) {
                if (checkUserMacAddress(user, macAddress)) {
                    List<Ticket> tickets;

                    if (ticketTypeId.matches("6")) {
                        tickets = ticketDao.getTicketArchiveList();
                    } else {
                        tickets = ticketDao.getTicketListByTicketTypeId(Short.parseShort(ticketTypeId), user.getUserId());
                    }
                    jsonObject.put("status", "ok");

                    JSONObject jsonItem = new JSONObject();
                    JSONArray arrayItem = new JSONArray();

                    for (Ticket ticket : tickets) {

                        JSONObject jsonObjectItem = new JSONObject();
                        jsonObjectItem.put("ticketId", ticket.getTicketId());
                        jsonObjectItem.put("sickFirstName", URLEncoder.encode(ticket.getFirstName(), "UTF-8"));
                        jsonObjectItem.put("sickLastName", URLEncoder.encode(ticket.getLastName(), "UTF-8"));
                        jsonObjectItem.put("sickNationalCode", ticket.getNationalCode());
                        jsonObjectItem.put("hospitalName", URLEncoder.encode(ticket.getHospital().getName(), "UTF-8"));
                        jsonObjectItem.put("sectionName", URLEncoder.encode(ticket.getSection().getTitle(), "UTF-8"));
                        jsonObjectItem.put("date", ticket.getSubmitDate());
                        jsonObjectItem.put("sendType", URLEncoder.encode(ticket.getSendType().getTitle(), "UTF-8"));
                        jsonObjectItem.put("status", URLEncoder.encode(ticket.getTicketStatus().getTitle(), "UTF-8"));
                        jsonObjectItem.put("statusId", ticket.getTicketStatus().getTicketStatusId());

                        arrayItem.put(jsonObjectItem);
                    }

                    jsonArray.put(jsonObject);

                    jsonItem.put("list", arrayItem);
                    jsonArray.put(jsonItem);

                    return jsonArray.toString();
                } else {
                    jsonObject.put("status", "101");
                    jsonObject.put("description", "Mac address not equal");

                    jsonArray.put(jsonObject);

                    return jsonArray.toString();
                }
            } else {
                jsonObject.put("status", "100");
                jsonObject.put("description", "Session failed");

                jsonArray.put(jsonObject);

                return jsonArray.toString();
            }
        } catch (Exception ex) {
            jsonObject.put("status", "error");
            jsonObject.put("description", ex.toString());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        }
    }

    @RequestMapping(value = "ws/api/getTicket", method = RequestMethod.POST)
    public
    @ResponseBody
    String getTicket(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String ticketId = URLDecoder.decode(parameter.get("TicketId").get(0), "UTF-8");
            String macAddress = URLDecoder.decode(parameter.get("MacAddress").get(0), "UTF-8");

            Users user = userDao.findUserByMacAddress(macAddress);

            if (checkUserSessionIsActive(user)) {
                if (checkUserMacAddress(user, macAddress)) {
                    Ticket ticket = ticketDao.findTicketById(Long.parseLong(ticketId));
                    List<TicketAttachment> ticketAttachmentList = ticketAttachmentDao.getTicketAttachmentListByTicketId(Long.parseLong(ticketId));

                    jsonObject.put("status", "ok");
                    jsonObject.put("ticket_Id", ticket.getTicketId());
                    jsonObject.put("ticketTypeId", ticket.getTicketType().getTicketTypeId());
                    jsonObject.put("ticketTypeTitle", URLEncoder.encode(ticket.getTicketType().getTitle(), "UTF-8"));
                    jsonObject.put("hospitalName", URLEncoder.encode(ticket.getHospital().getName(), "UTF-8"));
                    jsonObject.put("hospitalId", ticket.getHospital().getHospitalId());
                    jsonObject.put("sectionTitle", URLEncoder.encode(ticket.getSection().getTitle(), "UTF-8"));
                    jsonObject.put("sendType", URLEncoder.encode(ticket.getSendType().getTitle(), "UTF-8"));
                    jsonObject.put("name", URLEncoder.encode(ticket.getFirstName(), "UTF-8") + " " + URLEncoder.encode(ticket.getLastName(), "UTF-8"));
                    jsonObject.put("nationalCode", ticket.getNationalCode());
                    jsonObject.put("phoneNumber", ticket.getPhoneNumber());
                    jsonObject.put("mobile", ticket.getMobile());
                    jsonObject.put("tel", ticket.getPhoneNumber());
                    jsonObject.put("subject", URLEncoder.encode(ticket.getSubject(), "UTF-8"));
                    jsonObject.put("description", URLEncoder.encode(ticket.getDescription(), "UTF-8"));
                    jsonObject.put("submitDate", ticket.getSubmitDate());
                    jsonObject.put("email", ticket.getEmail());
                    jsonObject.put("trackingCode", ticket.getTrackingCode());
                    jsonObject.put("ticketAttachmentList", ticketAttachmentList);
                    jsonObject.put("statusId", ticket.getTicketStatus().getTicketStatusId());

                    if (ticket.getTicketType().getTicketTypeId() == Constant.Complaint) {
                        jsonObject.put("complaintTypeTitle", URLEncoder.encode(ticket.getComplaintType().getTitle(), "UTF-8"));
                        jsonObject.put("complainantTitle", URLEncoder.encode(ticket.getComplainant().getTitle(), "UTF-8"));

                        int complainatId = ticket.getComplainant().getComplainantId();
                        if (complainatId == 2) {
                            List<ComplainantRelation> complainantRelationList = complainantRelationDao.findComplainantRelationByTicketId(Long.parseLong(ticketId));
                            ComplainantRelation complainantRelation = complainantRelationList.get(0);

                            jsonObject.put("complainerName", URLEncoder.encode(complainantRelation.getFirstName(), "UTF-8") + " " + URLEncoder.encode(complainantRelation.getLastName(), "UTF-8"));
                            jsonObject.put("complainerNationalCode", complainantRelation.getNationalCode());

                        } else {
                            jsonObject.put("complainerName", "");
                            jsonObject.put("complainerNationalCode", "");
                        }
                    }

                    if (ticket.getTicketType().getTicketTypeId() != Constant.Offer) {
                        jsonObject.put("shiftTitle", URLEncoder.encode(ticket.getShift().getTitle(), "UTF-8"));
                    } else {
                        jsonObject.put("shiftTitle", "");
                    }

                    List<TicketErrand> ticketErrandList = ticketErrandDao.getTicketErrandListByTicketId(Long.parseLong(ticketId));

                    jsonObject.put("ticketErrand", ticketErrandList);


                    jsonArray.put(jsonObject);

                    return jsonArray.toString();
                } else {
                    jsonObject.put("status", "101");
                    jsonObject.put("description", "Session failed");

                    jsonArray.put(jsonObject);

                    return jsonArray.toString();
                }
            } else {
                jsonObject.put("status", "100");
                jsonObject.put("description", "Session failed");

                jsonArray.put(jsonObject);

                return jsonArray.toString();
            }

        } catch (Exception ex) {
            jsonObject.put("status", "error");
            jsonObject.put("description", ex.toString());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        }
    }

    @RequestMapping(value = "ws/api/endedTicket", method = RequestMethod.POST)
    public
    @ResponseBody
    String EndedTicket(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String ticketId = URLDecoder.decode(parameter.get("TicketId").get(0), "UTF-8");
            String macAddress = URLDecoder.decode(parameter.get("MacAddress").get(0), "UTF-8");

            Users user = userDao.findUserByMacAddress(macAddress);

            if (checkUserSessionIsActive(user)) {
                if (checkUserMacAddress(user, macAddress)) {
                    Long id = Long.parseLong(ticketId);
                    Ticket ticket = ticketDao.findTicketById(id);

                    TicketStatus ticketStatus = ticketStatusDao.findTicketStatusById(Constant.Ended);

                    ticket.setTicketStatus(ticketStatus);

                    ticketDao.saveTicket(ticket);

                    jsonObject.put("status", "ok");
                    jsonArray.put(jsonObject);

                    return jsonArray.toString();
                } else {
                    jsonObject.put("status", "101");
                    jsonObject.put("description", "Session failed");

                    jsonArray.put(jsonObject);

                    return jsonArray.toString();
                }
            } else {
                jsonObject.put("status", "100");
                jsonObject.put("description", "Session failed");

                jsonArray.put(jsonObject);

                return jsonArray.toString();
            }

        } catch (Exception ex) {
            jsonObject.put("status", "error");
            jsonObject.put("description", ex.toString());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        }
    }

    @RequestMapping(value = "ws/api/errandTicket", method = RequestMethod.POST)
    public
    @ResponseBody
    String errandTicket(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String ticketId = URLDecoder.decode(parameter.get("TicketId").get(0), "UTF-8");
            String userList = URLDecoder.decode(parameter.get("UserList").get(0), "UTF-8");
            String description = URLDecoder.decode(parameter.get("Description").get(0), "UTF-8");
            String macAddress = URLDecoder.decode(parameter.get("MacAddress").get(0), "UTF-8");

            Users user = userDao.findUserByMacAddress(macAddress);


            if (checkUserSessionIsActive(user)) {
                if (checkUserMacAddress(user, macAddress)) {

                    Ticket ticket = ticketDao.findTicketById(Long.parseLong(ticketId));
                    PersianCalendar persianCalendar = new PersianCalendar();
                    String currentDate = persianCalendar.getIranianSimpleDate();

                    String[] assignedUser = userList.split("_");

                    for (int i = 1; i < assignedUser.length; i++) {

                        int userId = Integer.parseInt(assignedUser[i]);

                        Users users = userDao.findUserById(userId);

                        TicketErrand ticketErrand = new TicketErrand();
                        ticketErrand.setTicket(ticket);
                        ticketErrand.setAssignedUser(users);
                        ticketErrand.setCreateUser(user);
                        ticketErrand.setSubmitDate(currentDate);
                        ticketErrand.setDescription(description);

                        ticketErrandDao.saveTicketErrand(ticketErrand);
                    }

                    TicketStatus ticketStatus = ticketStatusDao.findTicketStatusById(Constant.Errand);
                    ticket.setTicketStatus(ticketStatus);

                    ticketDao.saveTicket(ticket);

                    jsonObject.put("status", "ok");
                    jsonArray.put(jsonObject);

                    return jsonArray.toString();
                } else {
                    jsonObject.put("status", "101");
                    jsonObject.put("description", "Session failed");

                    jsonArray.put(jsonObject);

                    return jsonArray.toString();
                }
            } else {
                jsonObject.put("status", "100");
                jsonObject.put("description", "Session failed");

                jsonArray.put(jsonObject);

                return jsonArray.toString();
            }
        } catch (Exception ex) {
            jsonObject.put("status", "error");
            jsonObject.put("description", ex.toString());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        }
    }

    @RequestMapping(value = "ws/api/getAttachment", method = RequestMethod.POST)
    public
    @ResponseBody
    String getAttachment(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String ticketId = URLDecoder.decode(parameter.get("TicketId").get(0), "UTF-8");
            String macAddress = URLDecoder.decode(parameter.get("MacAddress").get(0), "UTF-8");

            Users user = userDao.findUserByMacAddress(macAddress);


            if (checkUserSessionIsActive(user)) {
                if (checkUserMacAddress(user, macAddress)) {

                    List<TicketAttachment> ticketAttachmentList = ticketAttachmentDao.getTicketAttachmentListByTicketId(Long.parseLong(ticketId));

                    if (ticketAttachmentList.size() > 0) {

                        String attachmentUrl = "http://192.168.1.5:8071/static/attachment/" + ticketAttachmentList.get(0).getFileName() + "." + ticketAttachmentList.get(0).getFileType();

                        jsonObject.put("status", "ok");
                        jsonObject.put("url", attachmentUrl);
                        jsonArray.put(jsonObject);

                        return jsonArray.toString();
                    } else {
                        jsonObject.put("status", "102");
                        jsonObject.put("description", "Attach not found");
                        jsonArray.put(jsonObject);

                        return jsonArray.toString();
                    }
                } else {
                    jsonObject.put("status", "101");
                    jsonObject.put("description", "Session failed");

                    jsonArray.put(jsonObject);

                    return jsonArray.toString();
                }
            } else {
                jsonObject.put("status", "100");
                jsonObject.put("description", "Session failed");

                jsonArray.put(jsonObject);

                return jsonArray.toString();
            }
        } catch (Exception ex) {
            jsonObject.put("status", "error");
            jsonObject.put("description", ex.toString());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        }
    }

    @RequestMapping(value = "ws/api/getSectionAndUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String getSectionAndUser(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String hospitalId = URLDecoder.decode(parameter.get("HospitalId").get(0), "UTF-8");
            String macAddress = URLDecoder.decode(parameter.get("MacAddress").get(0), "UTF-8");

            Users user = userDao.findUserByMacAddress(macAddress);


            if (checkUserSessionIsActive(user)) {
                if (checkUserMacAddress(user, macAddress)) {

                    List<HospitalSection> hospitalSectionList = hospitalSectionDao.getHospitalSectionsListByHospitalId(Integer.parseInt(hospitalId));
                    jsonObject.put("status", "ok");
                    jsonArray.put(jsonObject);

                    JSONObject sectionObject = new JSONObject();
                    JSONArray sectionArray = new JSONArray();

                    for (HospitalSection hospitalSection : hospitalSectionList) {
                        JSONObject sectionItem = new JSONObject();

                        int sectionId = hospitalSection.getSection().getSectionId();

                        sectionItem.put("sectionId", sectionId);
                        sectionItem.put("Title", URLEncoder.encode(hospitalSection.getSection().getTitle(), "UTF-8"));


                        List<UsersHospitalSection> userSectionList = userSectionDao.getUserSectionBySectionId(sectionId, Integer.parseInt(hospitalId));

                        JSONArray userItemArray = new JSONArray();

                        for (UsersHospitalSection userSection : userSectionList) {
                            JSONObject userItem = new JSONObject();

                            Users userDataItem = userDao.findUserById(userSection.getUser().getUserId());

                            userItem.put("userId", userDataItem.getUserId());
                            userItem.put("name", URLEncoder.encode(userDataItem.getFirstName(), "UTF-8") + " " + URLEncoder.encode(userDataItem.getLastName(), "UTF-8"));

                            userItemArray.put(userItem);
                        }

                        sectionItem.put("userList", userItemArray);

                        sectionArray.put(sectionItem);
                    }

                    sectionObject.put("sectionList", sectionArray);

                    jsonArray.put(sectionObject);

                    return jsonArray.toString();
                } else {
                    jsonObject.put("status", "101");
                    jsonObject.put("description", "Session failed");

                    jsonArray.put(jsonObject);

                    return jsonArray.toString();
                }
            } else {
                jsonObject.put("status", "100");
                jsonObject.put("description", "Session failed");

                jsonArray.put(jsonObject);

                return jsonArray.toString();
            }
        } catch (Exception ex) {
            jsonObject.put("status", "error");
            jsonObject.put("description", ex.toString());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        }
    }

    private Map<String, List<String>> splitQuery(URL url) throws UnsupportedEncodingException {
        final Map<String, List<String>> query_pairs = new LinkedHashMap<String, List<String>>();

        final String[] pairs = url.getQuery().split("&");

        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;

            if (!query_pairs.containsKey(key)) {
                query_pairs.put(key, new LinkedList<String>());
            }

            final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
            query_pairs.get(key).add(value);
        }

        return query_pairs;
    }
}
