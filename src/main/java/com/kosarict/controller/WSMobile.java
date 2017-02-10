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

            List<Tab> tabList = tabDao.getAllTabListByUserId(user.getUserId());

            if (user != null) {
                if (user.getPassword() == password && user.getLocked() == 0 && macAddress==user.getMacAddress()) {
                    jsonObject.put("status", "ok");
                    jsonObject.put("description", "ok");
                    jsonObject.put("tabList", tabList);

                } else if (user.getPassword() != password) {
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
            if(!isLogin(model))
                return String.valueOf(false);
            return jsonArray.toString();
        } catch (Exception ex) {
            jsonObject.put("status", "error");
            jsonObject.put("description", ex.toString());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        }
    }

    boolean isLogin(String model){
        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);
            String userName = URLDecoder.decode(parameter.get("User").get(0), "UTF-8");
            String macAddress = URLDecoder.decode(parameter.get("MacAddress").get(0), "UTF-8");

            Users user = userDao.findUserByUserName(userName);
            if(user.getMacAddress()==macAddress)
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

        if(!isLogin(model))
            return String.valueOf(false);
        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String userName = URLDecoder.decode(parameter.get("User").get(0), "UTF-8");
            String ticketTypeId = URLDecoder.decode(parameter.get("TicketTypeId").get(0), "UTF-8");

            Users user = userDao.findUserByUserName(userName);

            List<Ticket> tickets = ticketDao.getTicketListByTicketTypeId(Short.parseShort(ticketTypeId), user.getUserId());

            jsonObject.put("status", "ok");
            jsonObject.put("tickets", tickets);
            jsonArray.put(jsonObject);

            return jsonArray.toString();
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

        if(!isLogin(model))
            return String.valueOf(false);
        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String ticketId = URLDecoder.decode(parameter.get("TicketId").get(0), "UTF-8");

            Ticket ticket = ticketDao.findTicketById(Long.parseLong(ticketId));
            List<TicketAttachment> ticketAttachmentList = ticketAttachmentDao.getTicketAttachmentListByTicketId(Long.parseLong(ticketId));

            jsonObject.put("status", "ok");
            jsonObject.put("tickets", ticket);
            jsonObject.put("tickets", ticketAttachmentList);
            jsonArray.put(jsonObject);

            return jsonArray.toString();
        } catch (Exception ex) {
            jsonObject.put("status", "error");
            jsonObject.put("description", ex.toString());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        }
    }

    @RequestMapping(value = "ws/api/Ended", method = RequestMethod.POST)
    public
    @ResponseBody
    String EndedTicket(@RequestBody String model)  {
        try {
            if(!isLogin(model))
                return String.valueOf(false);
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String ticketId = URLDecoder.decode(parameter.get("TicketId").get(0), "UTF-8");

            Long id = Long.parseLong(ticketId);
            Ticket ticket = ticketDao.findTicketById(id);

            TicketStatus ticketStatus = ticketStatusDao.findTicketStatusById(Constant.Ended);

            ticket.setTicketStatus(ticketStatus);

            ticketDao.saveTicket(ticket);

            return String.valueOf(true);
        }catch (Exception ex){
            return String.valueOf(false);
        }
    }

    @RequestMapping(value = "ws/api/Errand", method = RequestMethod.POST)
    public
    @ResponseBody
    String ErrandTicket(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray(model);
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        if(!isLogin(model))
            return String.valueOf(false);
        long ticketId = jsonObject.getLong("ticketId");
        Integer creatorId = jsonObject.getInt("creatorId");
        int assignedlId = jsonObject.getInt("assigneId");
        String description = jsonObject.getString("description");
        Ticket ticket = ticketDao.findTicketById(ticketId);
        Users creatorUser = userDao.findUserById(creatorId);
        Users assignedUser = userDao.findUserById(assignedlId);
        PersianCalendar persianCalendar = new PersianCalendar();
        String currentDate = persianCalendar.getIranianSimpleDate();

        TicketErrand ticketErrand = new TicketErrand();
        ticketErrand.setTicket(ticket);
        ticketErrand.setAssignedUser(assignedUser);
        ticketErrand.setCreateUser(creatorUser);
        ticketErrand.setSubmitDate(currentDate);
        ticketErrand.setDescription(description);

        ticketErrandDao.saveTicketErrand(ticketErrand);

        return String.valueOf(true);
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
