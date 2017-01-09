package com.kosarict.controller;

import com.kosarict.dao.*;
import com.kosarict.entity.*;
import com.kosarict.model.Constant;
import com.kosarict.tools.PersianCalendar;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Sadegh-Pc on 12/5/2016.
 */
@Controller
public class ADComplainController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private HospitalSectionDao hospitalSectionDao;

    @Autowired


    @RequestMapping(value = "/adComplain", method = RequestMethod.GET)
    public ModelAndView getComplainView() {
        ModelAndView model = new ModelAndView("adComplain");
        model.addObject("complainLists", getComplainList());
        model.addObject("hospitalSectionList", getSectionList());

        return model;
    }

    @RequestMapping(value = "/adComplain/api/getData", method = RequestMethod.GET)
    public
    @ResponseBody
    String getData() {
        JSONArray jsonArray = new JSONArray();

        for (Ticket ticket : getComplainList()) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("ticketId", ticket.getTicketId());
            jsonObject.put("complainantTitle", ticket.getComplainant().getTitle());
            jsonObject.put("name", ticket.getFirstName() + " " + ticket.getLastName());
            jsonObject.put("nationalCode", ticket.getNationalCode());
            jsonObject.put("hospitalName", ticket.getHospital().getName());
            jsonObject.put("complaintTypeTitle", ticket.getComplaintType().getTitle());
            jsonObject.put("submitDate", ticket.getSubmitDate());
            jsonObject.put("sendTypeTitle", ticket.getSendType().getTitle());

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    private List<Ticket> getComplainList() {
        return ticketDao.getTicketListByTicketTypeId(Constant.ComplainTicketTypeId);
    }

    private List<HospitalSection> getSectionList() {
        return hospitalSectionDao.getHospitalSectionsListByHospitalId(Constant.hospitalId);
    }

}
