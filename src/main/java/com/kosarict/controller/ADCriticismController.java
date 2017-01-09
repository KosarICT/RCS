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
 * Created by Younes on 17/12/2016.
 */
@Controller
public class ADCriticismController {
    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private ADCriticismDao criticismDao;

    @Autowired
    private UserSectionDao userSectionDao;

    @Autowired
    private HospitalSectionDao hospitalSectionDao;
    @Autowired
    private ADCriticizeAttachmentDao criticizeAttachmentDao;


    @RequestMapping(value = "/adCriticism", method = RequestMethod.GET)
    public ModelAndView getComplainView() {
        ModelAndView model = new ModelAndView("adCriticism");
        model.addObject("criticismLists", getCriticismList());
        model.addObject("hospitalSectionList", getSectionList());

        return model;
    }

    @RequestMapping(value = "/adCriticism/api/getCriticismData", method = RequestMethod.GET)
    public
    @ResponseBody
    String getCriticismData() {
        JSONArray jsonArray = new JSONArray();

        for (Ticket ticket : getCriticismList()) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("ticketId", ticket.getTicketId());
            jsonObject.put("name", ticket.getFirstName() + " " + ticket.getLastName());
            jsonObject.put("nationalCode", ticket.getNationalCode());
            jsonObject.put("subject", ticket.getSubject());
            jsonObject.put("hospitalName", ticket.getHospital().getName());
            jsonObject.put("sectionTitle", ticket.getSection().getTitle());
            jsonObject.put("submitDate", ticket.getSubmitDate());
            jsonObject.put("sendTypeTitle", ticket.getSendType().getTitle());
            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    private List<Ticket> getCriticismList() {
        return ticketDao.getTicketListByTicketTypeId(Constant.CriticismTicketTypeId);
    }

    private List<HospitalSection> getSectionList() {
        return hospitalSectionDao.getHospitalSectionsListByHospitalId(Constant.hospitalId);
    }

}
