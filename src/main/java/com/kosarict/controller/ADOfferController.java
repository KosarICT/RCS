package com.kosarict.controller;

import com.kosarict.dao.HospitalSectionDao;
import com.kosarict.dao.TicketDao;
import com.kosarict.entity.HospitalSection;
import com.kosarict.entity.Ticket;
import com.kosarict.model.Constant;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Younes on 17/12/2016.
 */
@Controller
public class ADOfferController {

    @Autowired
    private HospitalSectionDao hospitalSectionDao;

    @Autowired
    private TicketDao ticketDao;

    @RequestMapping(value = "/adOffer", method = RequestMethod.GET)
    public ModelAndView getAppreciationView() {
        ModelAndView model = new ModelAndView("adOffer");

        model.addObject("offerList", getOfferList());
        model.addObject("hospitalSectionList", getSectionList());
        return model;
    }

    @RequestMapping(value = "/adOffer/api/getAllOfferData", method = RequestMethod.GET)
    public
    @ResponseBody
    String getAllOfferData() {
        JSONArray jsonArray = new JSONArray();

        for (Ticket ticket : getOfferList()) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("ticketId", ticket.getTicketId());
            jsonObject.put("name", ticket.getFirstName() + " " + ticket.getLastName());
            jsonObject.put("nationalCode", ticket.getNationalCode());
            jsonObject.put("subject", ticket.getSubject());
            jsonObject.put("hospitalName", ticket.getHospital().getName());
            jsonObject.put("sectionName", ticket.getSection().getTitle());
            jsonObject.put("submitDate", ticket.getSubmitDate());
            jsonObject.put("sendTypeTitle", ticket.getSendType().getTitle());

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    private List<Ticket> getOfferList() {
        return ticketDao.getTicketListByTicketTypeId(Constant.OfferTicketTypeId);
    }

    private List<HospitalSection> getSectionList() {
        return hospitalSectionDao.getHospitalSectionsListByHospitalId(Constant.hospitalId);
    }
}
