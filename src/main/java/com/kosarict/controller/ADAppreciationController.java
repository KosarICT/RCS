package com.kosarict.controller;

import com.kosarict.dao.HospitalSectionDao;
import com.kosarict.dao.TicketDao;
import com.kosarict.entity.HospitalSection;
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
 * Created by Younes on 16/12/2016.
 */
@Controller
public class ADAppreciationController {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private HospitalSectionDao hospitalSectionDao;

    @RequestMapping(value = "/adAppreciation", method = RequestMethod.GET)
    public ModelAndView getAppreciationView() {
        ModelAndView model = new ModelAndView("adAppreciation");

        model.addObject("appreciationList", getAppreciationList());
        model.addObject("hospitalSectionList", getSectionList());
        return model;
    }

    @RequestMapping(value = "/adAppreciation/api/getAllAppreciationData", method = RequestMethod.GET)
    public
    @ResponseBody
    String getAllAppreciationData() {
        JSONArray jsonArray = new JSONArray();

        for (com.kosarict.entity.Ticket ticket : getAppreciationList()) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("ticketId", ticket.getTicketId());
            jsonObject.put("name", ticket.getFirstName() + " " + ticket.getLastName());
            jsonObject.put("personnelName", ticket.getPersnolFirstName() + " " + ticket.getPersnolLastName());
            jsonObject.put("nationalCode", ticket.getNationalCode());
            jsonObject.put("hospitalName", ticket.getHospital().getName());
            jsonObject.put("sectionName", ticket.getSection().getTitle());
            jsonObject.put("submitDate", ticket.getSubmitDate());
            jsonObject.put("sendTypeTitle", ticket.getSendType().getTitle());

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    private List<com.kosarict.entity.Ticket> getAppreciationList() {
        return ticketDao.getTicketListByTicketTypeId(Constant.AppreciationTicketTypeId);
    }

    private List<HospitalSection> getSectionList() {
        return hospitalSectionDao.getHospitalSectionsListByHospitalId(Constant.hospitalId);
    }

}
