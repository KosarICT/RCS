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
    private ComplainantRelationDao getComplainantRelationDao;

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

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private HospitalDao hospitalDao;

    @Autowired
    private RequestStatusDao requestStatusDao;


    /**
     * set request for hospital
     */
    @RequestMapping(value = "ws/api/setRequest", method = RequestMethod.POST)
    public
    @ResponseBody
    String setRequest(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String hospitalId = URLDecoder.decode(parameter.get("HospitalId").get(0), "UTF-8");
            String nationalCode = URLDecoder.decode(parameter.get("NationalCode").get(0), "UTF-8");
            String imei = URLDecoder.decode(parameter.get("IMEI").get(0), "UTF-8");

            Users user = userDao.findUserByNationalCode(nationalCode);

            Hospital hospital = hospitalDao.findHospitalById(Integer.parseInt(hospitalId));
            RequestStatus requestStatus = requestStatusDao.findRequestStatusById(Constant.PendingStatus);

            if (user != null) {

                Request requestItem = requestDao.findRequestByUserIdAndHospitalId(user.getUserId(), Integer.parseInt(hospitalId));

                if (requestItem != null) {
                    jsonObject.put("status", "801");
                    jsonObject.put("description", "Request submited");
                } else {
                    Request request = new Request();
                    request.setRequestId(0);
                    request.setHospital(hospital);
                    request.setUser(user);
                    request.setRequestStatus(requestStatus);

                    int newRequestId = requestDao.saveRequest(request);

                    user.setImei(imei);

                    int newUserId = userDao.saveUser(user);

                    jsonObject.put("status", "ok");
                }
            } else {
                jsonObject.put("status", "800");
                jsonObject.put("description", "User not found");
            }


            jsonArray.put(jsonObject);

            return jsonArray.toString();
        } catch (Exception ex) {
            jsonObject.put("status", "error");
            jsonObject.put("description", ex.toString());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        }
    }


    /**
     * get request list of user
     */
    @RequestMapping(value = "ws/api/getRequestList", method = RequestMethod.POST)
    public
    @ResponseBody
    String getRequestList(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String imei = URLDecoder.decode(parameter.get("IMEI").get(0), "UTF-8");

            Users user = userDao.findUserByImei(imei);

            JSONArray requestArray = new JSONArray();


            jsonObject.put("status", "ok");

            if (user != null) {
                List<Request> requestList = requestDao.getRequestList(user.getUserId());

                for (Request request : requestList) {
                    JSONObject jsonItem = new JSONObject();

                    jsonItem.put("hospitalId", request.getHospital().getHospitalId());
                    jsonItem.put("hospitalTitle", URLEncoder.encode(request.getHospital().getName(), "UTF-8"));
                    jsonItem.put("statusId", request.getRequestStatus().getRequestStatusId());
                    jsonItem.put("statusTitle", URLEncoder.encode(request.getRequestStatus().getTitle(), "UTF-8"));

                    requestArray.put(jsonItem);
                }
            }

            jsonObject.put("requestList", requestArray);

            jsonArray.put(jsonObject);

            return jsonArray.toString();

        } catch (Exception ex) {
            jsonObject.put("status", "error");
            jsonObject.put("description", ex.toString());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        }
    }


    /**
     * login to system
     */
    @RequestMapping(value = "/ws/api/loginUser", method = RequestMethod.POST)
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
            String hospitalId = URLDecoder.decode(parameter.get("HospitalId").get(0), "UTF-8");
            String imei = URLDecoder.decode(parameter.get("IMEI").get(0), "UTF-8");

            Users user = userDao.findUserByUserName(userName);

            if (user != null) {
                Request request = requestDao.findRequestByUserIdAndHospitalId(user.getUserId(), Integer.parseInt(hospitalId));
                Hospital hospital = hospitalDao.findHospitalById(Integer.parseInt(hospitalId));

                List<UserRole> role = userRoleDao.getUserRole(user.getUserId());

                JSONArray tabArray = new JSONArray();
                JSONArray userArray = new JSONArray();


                if (user.getPassword().matches(password) && request.getRequestStatus().getRequestStatusId() == Constant.EnableStatus && imei.matches(user.getImei())) {
                    jsonObject.put("status", "ok");
                    jsonObject.put("description", "ok");
                    jsonObject.put("userServerId", user.getUserId());


                    jsonObject.put("tabList", tabArray);

                    JSONObject userObject = new JSONObject();
                    userObject.put("fullName", URLEncoder.encode(user.getFirstName() + " " + user.getLastName(), "UTF-8"));
                    userObject.put("imageUrl", Constant.userImageUrl + user.getImageName());
                    userObject.put("role", URLEncoder.encode(
                            role.get(0).getRole().getName() + " "
                                    + hospital.getName(), "UTF-8"));

                    userArray.put(userObject);

                    jsonObject.put("user", userArray);

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


    /**
     * get list of ticket by ticket type
     */
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
            String hospitalId = URLDecoder.decode(parameter.get("HospitalId").get(0), "UTF-8");
            String imei = URLDecoder.decode(parameter.get("IMEI").get(0), "UTF-8");

            Users user = userDao.findUserByImei(imei);


            if (checkUserSessionIsActive(user)) {
                if (checkUserImei(user, imei)) {
                    List<Ticket> tickets;

                    if (ticketTypeId.matches("6")) {
                        tickets = ticketDao.getTicketArchiveList();
                    } else {
                        tickets = ticketDao.getTicketList(Short.parseShort(ticketTypeId), user.getUserId(), Integer.parseInt(hospitalId));
                    }
                    jsonObject.put("status", "ok");


                    int ticketUnReadCount = ticketDao.getUnReadTicket(user.getUserId(), Integer.parseInt(ticketTypeId));
                    int ticketErrandCount = ticketDao.getTicketErrandedCount(user.getUserId(), Integer.parseInt(ticketTypeId));
                    int ticketReadCount = ticketDao.getReadTicket(user.getUserId(), Integer.parseInt(ticketTypeId));


                    jsonObject.put("unreadCount", ticketUnReadCount);
                    jsonObject.put("errandCount", ticketErrandCount);
                    jsonObject.put("readCount", ticketReadCount);

                    JSONObject jsonItem = new JSONObject();
                    JSONArray arrayItem = new JSONArray();


                    for (Ticket ticket : tickets) {

                        JSONObject jsonObjectItem = new JSONObject();

                        jsonObjectItem.put("ticketId", ticket.getTicketId());
                        jsonObjectItem.put("date", ticket.getSubmitDate());
                        jsonObjectItem.put("trackingCode", ticket.getTrackingCode());
                        jsonObjectItem.put("sendTypeId", ticket.getSendType().getSendTypeId());
                        jsonObjectItem.put("subject", URLEncoder.encode(ticket.getSubject(), "UTF-8"));
                        jsonObjectItem.put("responceDay", ticket.getComplaintType().getResponceTime());

                        TicketErrand ticketErrand = getErrand(ticket.getTicketId(), user.getUserId());

                        if (ticketErrand != null) {
                            jsonObjectItem.put("senderType", "1");
                            jsonObjectItem.put("sender", ticketErrand.getCreateUser().getFirstName() + " " + ticketErrand.getCreateUser().getLastName());
                        } else if (ticket.getComplainant() != null) {
                            jsonObjectItem.put("senderType", "2");
                            jsonObjectItem.put("sender", URLEncoder.encode(ticket.getFirstName() + " " + ticket.getLastName(), "UTF-8"));
                        } else {
                            jsonObjectItem.put("senderType", "3");
                            jsonObjectItem.put("sender", URLEncoder.encode("همراه بیمار", "UTF-8"));
                        }


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


    /**
     * get ticket data by ticket id
     */
    @RequestMapping(value = "ws/api/searchTicketByTrackingCode", method = RequestMethod.POST)
    public
    @ResponseBody
    String searchTicketByTrackingCode(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String trackingCode = URLDecoder.decode(parameter.get("TrackingCode").get(0), "UTF-8");
            String imei = URLDecoder.decode(parameter.get("IMEI").get(0), "UTF-8");

            Users user = userDao.findUserByImei(imei);

            if (checkUserSessionIsActive(user)) {
                if (checkUserImei(user, imei)) {
                    Ticket ticket = ticketDao.findTicketByTrackingCode(trackingCode);

                    if (ticket != null) {
                        List<TicketAttachment> ticketAttachmentList = ticketAttachmentDao.getTicketAttachmentListByTicketId(ticket.getTicketId());

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
                                List<ComplainantRelation> complainantRelationList = complainantRelationDao.findComplainantRelationByTicketId(ticket.getTicketId());
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

                        List<TicketErrand> ticketErrandList = ticketErrandDao.getTicketErrandListByTicketId(ticket.getTicketId());

                        jsonObject.put("ticketErrand", ticketErrandList);


                        jsonArray.put(jsonObject);
                    } else {
                        jsonObject.put("status", "900");
                        jsonObject.put("description", "Ticket not found!!!");

                        jsonArray.put(jsonObject);
                    }

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


    /**
     * search ticket by tracking code
     */
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
            String imei = URLDecoder.decode(parameter.get("IMEI").get(0), "UTF-8");

            Users user = userDao.findUserByImei(imei);

            if (checkUserSessionIsActive(user)) {
                if (checkUserImei(user, imei)) {
                    Ticket ticket = ticketDao.findTicketById(Long.parseLong(ticketId));
                    List<TicketAttachment> ticketAttachmentList = ticketAttachmentDao.getTicketAttachmentListByTicketId(Long.parseLong(ticketId));

                    jsonObject.put("status", "ok");
                    jsonObject.put("ticket_Id", ticket.getTicketId());

                    jsonObject.put("trackingCode", ticket.getTrackingCode());
                    jsonObject.put("name", URLEncoder.encode(ticket.getFirstName(), "UTF-8") + " " + URLEncoder.encode(ticket.getLastName(), "UTF-8") + "-" + URLEncoder.encode(ticket.getComplainant().getTitle(), "UTF-8"));
                    jsonObject.put("mobile", ticket.getMobile());
                    jsonObject.put("nationalCode", ticket.getNationalCode());
                    jsonObject.put("date", ticket.getSubmitDate());
                    jsonObject.put("description", URLEncoder.encode(ticket.getDescription(), "UTF-8"));
                    jsonObject.put("sendType", URLEncoder.encode(ticket.getSendType().getTitle(), "UTF-8"));
                    jsonObject.put("sendTypeId", ticket.getSendType().getSendTypeId());
                    jsonObject.put("ticketAttachmentList", ticketAttachmentList);
                    jsonObject.put("subject", URLEncoder.encode(ticket.getSubject(), "UTF-8"));

                    List<TicketErrand> ticketErrandList = ticketErrandDao.getTicketErrandListByTicketId(Long.parseLong(ticketId));


                    JSONArray errandArray = new JSONArray();

                    for (TicketErrand ticketErrand : ticketErrandList) {

                        if(ticketErrand.getCreateUser() == null){
                            continue;
                        }
                        JSONObject object = new JSONObject();

                        List<UserRole> role = userRoleDao.getUserRole(ticketErrand.getCreateUser().getUserId());

                        object.put("fullName", URLEncoder.encode(ticketErrand.getCreateUser().getFirstName() + " " + ticketErrand.getCreateUser().getLastName() + "-" + role.get(0).getRole().getName(), "UTF-8"));
                        object.put("description", URLEncoder.encode(ticketErrand.getDescription(), "UTF-8"));
                        object.put("date", ticketErrand.getSubmitDate());

                        errandArray.put(object);
                    }

                    jsonObject.put("ticketErrand", errandArray);


                    TicketStatus ticketStatus = ticketStatusDao.findTicketStatusById((short) Constant.IsRead);

                    ticket.setTicketStatus(ticketStatus);

                    ticketDao.saveTicket(ticket);

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


    /**
     * get all ticket info
     */
    @RequestMapping(value = "ws/api/getTicketDescription", method = RequestMethod.POST)
    public
    @ResponseBody
    String getTicketDescription(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String ticketId = URLDecoder.decode(parameter.get("TicketId").get(0), "UTF-8");
            String imei = URLDecoder.decode(parameter.get("IMEI").get(0), "UTF-8");

            Users user = userDao.findUserByImei(imei);

            if (checkUserSessionIsActive(user)) {
                if (checkUserImei(user, imei)) {
                    Ticket ticket = ticketDao.findTicketById(Long.parseLong(ticketId));
                    List<TicketAttachment> ticketAttachmentList = ticketAttachmentDao.getTicketAttachmentListByTicketId(Long.parseLong(ticketId));

                    jsonObject.put("status", "ok");
                    jsonObject.put("ticket_Id", ticket.getTicketId());
                    jsonObject.put("ticketTypeId", ticket.getTicketType().getTicketTypeId());
                    jsonObject.put("ticketTypeTitle", URLEncoder.encode(ticket.getTicketType().getTitle(), "UTF-8"));
                    jsonObject.put("hospitalName", URLEncoder.encode(ticket.getHospital().getName(), "UTF-8"));
                    jsonObject.put("hospitalId", ticket.getHospital().getHospitalId());
                    jsonObject.put("sectionTitle", URLEncoder.encode(ticket.getSection().getTitle(), "UTF-8"));


                    jsonObject.put("phoneNumber", ticket.getPhoneNumber());

                    jsonObject.put("tel", ticket.getPhoneNumber());


                    jsonObject.put("submitDate", ticket.getSubmitDate());
                    jsonObject.put("email", ticket.getEmail());
                    jsonObject.put("trackingCode", ticket.getTrackingCode());

                    jsonObject.put("statusId", ticket.getTicketStatus().getTicketStatusId());

                    jsonObject.put("trackingCode", ticket.getTrackingCode());
                    jsonObject.put("name", URLEncoder.encode(ticket.getFirstName(), "UTF-8") + " " + URLEncoder.encode(ticket.getLastName(), "UTF-8") + "-" + ticket.getComplainant().getTitle());
                    jsonObject.put("mobile", ticket.getMobile());
                    jsonObject.put("nationalCode", ticket.getNationalCode());
                    jsonObject.put("date", ticket.getSubmitDate());
                    jsonObject.put("description", URLEncoder.encode(ticket.getDescription(), "UTF-8"));
                    jsonObject.put("sendType", URLEncoder.encode(ticket.getSendType().getTitle(), "UTF-8"));
                    jsonObject.put("sendTypeId", ticket.getSendType().getSendTypeId());
                    jsonObject.put("ticketAttachmentList", ticketAttachmentList);
                    jsonObject.put("subject", URLEncoder.encode(ticket.getSubject(), "UTF-8"));

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


    /**
     * set answer for ticket
     */
    @RequestMapping(value = "ws/api/setTicketAnswer", method = RequestMethod.POST)
    public
    @ResponseBody
    String setTicketAnswer(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String ticketId = URLDecoder.decode(parameter.get("TicketId").get(0), "UTF-8");
            String Answer = URLDecoder.decode(parameter.get("Answer").get(0), "UTF-8");
            String imei = URLDecoder.decode(parameter.get("IMEI").get(0), "UTF-8");

            Users user = userDao.findUserByImei(imei);

            if (checkUserSessionIsActive(user)) {
                if (checkUserImei(user, imei)) {
                    Ticket ticket = ticketDao.findTicketById(Long.parseLong(ticketId));

                    ticket.setAnswer(Answer);

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


    /**
     * get answer list of ticket
     */
    @RequestMapping(value = "ws/api/getTicketAnswerList", method = RequestMethod.POST)
    public
    @ResponseBody
    String getTicketAnswerList(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String ticketId = URLDecoder.decode(parameter.get("TicketId").get(0), "UTF-8");
            String imei = URLDecoder.decode(parameter.get("IMEI").get(0), "UTF-8");

            Users user = userDao.findUserByImei(imei);

            if (checkUserSessionIsActive(user)) {
                if (checkUserImei(user, imei)) {
                    Ticket ticket = ticketDao.findTicketById(Long.parseLong(ticketId));

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


    /**
     * set end status for ticket
     */
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
            String imei = URLDecoder.decode(parameter.get("IMEI").get(0), "UTF-8");

            Users user = userDao.findUserByImei(imei);

            if (checkUserSessionIsActive(user)) {
                if (checkUserImei(user, imei)) {
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


    /**
     * set errand status for ticket
     */
    @RequestMapping(value = "ws/api/errandTicket", method = RequestMethod.POST)
    public
    @ResponseBody
    String errandTicket(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String errandArray = URLDecoder.decode(parameter.get("ErrandArray").get(0), "UTF-8");
            String userList = URLDecoder.decode(parameter.get("UserArray").get(0), "UTF-8");
            String description = URLDecoder.decode(parameter.get("Description").get(0), "UTF-8");
            String imei = URLDecoder.decode(parameter.get("IMEI").get(0), "UTF-8");
            String ticketId = URLDecoder.decode(parameter.get("TicketId").get(0), "UTF-8");

            Users user = userDao.findUserByImei(imei);


            if (checkUserSessionIsActive(user)) {
                if (checkUserImei(user, imei)) {

                    Ticket ticket = ticketDao.findTicketById(Long.parseLong(ticketId));

                    Calendar calendar = Calendar.getInstance();
                    PersianCalendar persianCalendar = new PersianCalendar();
                    String currentDate = persianCalendar.getIranianSimpleDate() + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

                    JSONArray errandJsonArray = new JSONArray(errandArray);

                    for (int i = 0; i < errandJsonArray.length(); i++) {
                        JSONObject object = errandJsonArray.getJSONObject(i);

                        int userId = Integer.parseInt(object.getString("id"));

                        Users users = userDao.findUserById(userId);

                        if(users.getUserId() == user.getUserId()){
                            continue;
                        }

                        TicketErrand ticketErrand = new TicketErrand();
                        ticketErrand.setTicket(ticket);
                        ticketErrand.setAssignedUser(users);
                        ticketErrand.setCreateUser(user);
                        ticketErrand.setSubmitDate(currentDate);
                        ticketErrand.setDescription(description);
                        ticketErrand.setView(true);

                        ticketErrandDao.saveTicketErrand(ticketErrand);
                    }

                    JSONArray userJsonArray = new JSONArray(userList);

                    for (int i = 0; i < userJsonArray.length(); i++) {
                        JSONObject object = userJsonArray.getJSONObject(i);

                        int userId = Integer.parseInt(object.getString("id"));

                        Users users = userDao.findUserById(userId);

                        if(users.getUserId() == user.getUserId()){
                            continue;
                        }

                        TicketErrand ticketErrand = new TicketErrand();
                        ticketErrand.setTicket(ticket);
                        ticketErrand.setAssignedUser(users);
                        ticketErrand.setCreateUser(user);
                        ticketErrand.setSubmitDate(currentDate);
                        ticketErrand.setDescription(description);
                        ticketErrand.setView(false);

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


    /***/
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
            String imei = URLDecoder.decode(parameter.get("IMEI").get(0), "UTF-8");

            Users user = userDao.findUserByImei(imei);


            if (checkUserSessionIsActive(user)) {
                if (checkUserImei(user, imei)) {

                    List<UsersHospitalSection> userSectionList = userSectionDao.getUserSectionByHospitalId(Integer.parseInt(hospitalId));

                    jsonObject.put("status", "ok");
                    jsonArray.put(jsonObject);

                    JSONArray userItemArray = new JSONArray();

                    for (UsersHospitalSection userSection : userSectionList) {
                        JSONObject userItem = new JSONObject();

                        Users userDataItem = userDao.findUserById(userSection.getUser().getUserId());

                        List<UserRole> role = userRoleDao.getUserRole(userDataItem.getUserId());


                        userItem.put("userId", userDataItem.getUserId());
                        userItem.put("fullName", URLEncoder.encode(userDataItem.getFirstName() + " " + userDataItem.getLastName() + "-" + role.get(0).getRole().getName(), "UTF-8"));

                        userItemArray.put(userItem);
                    }

                    jsonObject.put("sectionList", userItemArray);

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
            String imei = URLDecoder.decode(parameter.get("IMEI").get(0), "UTF-8");

            Users user = userDao.findUserByPersonalNumber(personalNumber);

            user.setImei(imei);

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

            String imei = URLDecoder.decode(parameter.get("IMEI").get(0), "UTF-8");

            Users user = userDao.findUserByImei(imei);

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
            String imei = URLDecoder.decode(parameter.get("IMEI").get(0), "UTF-8");

            Users user = userDao.findUserByImei(imei);

            if (checkUserSessionIsActive(user)) {
                if (checkUserImei(user, imei)) {
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
            String imei = URLDecoder.decode(parameter.get("IMEI").get(0), "UTF-8");

            Users user = userDao.findUserByImei(imei);


            if (checkUserSessionIsActive(user)) {
                if (checkUserImei(user, imei)) {

                    List<TicketAttachment> ticketAttachmentList = ticketAttachmentDao.getTicketAttachmentListByTicketId(Long.parseLong(ticketId));

                    if (ticketAttachmentList.size() > 0) {

                        String attachmentUrl = Constant.attachmentUrl + ticketAttachmentList.get(0).getFileName() + "." + ticketAttachmentList.get(0).getFileType();

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


    private List<HospitalSection> getSectionList(Users user) {
        return hospitalSectionDao.getHospitalSectionsListByHospitalId(getCurrentHospital(user).get(0).getHospitalSection().getHospital().getHospitalId());
    }

    private List<UsersHospitalSection> getCurrentHospital(Users user) {
        List<UsersHospitalSection> usersHospitalSectionList = userSectionDao.findUserHospitalSectionByUserId(user.getUserId());

        int hospitalId = 0;

        for (UsersHospitalSection usersHospitalSection : usersHospitalSectionList) {
            hospitalId = usersHospitalSection.getHospitalSection().getHospital().getHospitalId();
        }

        return usersHospitalSectionList;
    }

    private TicketErrand getErrand(long ticketId, int userId) {
        {
            List<TicketErrand> ticketErrandList = ticketErrandDao.getTicketErrandListByTicketId(ticketId, userId);

            if (ticketErrandList.size() > 0) {
                return ticketErrandList.get(0);
            } else {
                return null;
            }
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

    private boolean checkUserImei(Users user, String imei) {
        try {
            if (user.getImei().matches(imei))
                return true;

            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
