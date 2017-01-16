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

        return model;
    }

    @RequestMapping(value = "/adOffer/api/getAllAppreciationData", method = RequestMethod.GET)
    public
    @ResponseBody
    String getAllAppreciationData() {
        try {

            JSONArray ticketArray = new JSONArray();

            List<Ticket> ticketList = ticketDao.getTicketListByTicketTypeId(Constant.AppreciationTicketTypeId);

            for (Ticket ticket : ticketList) {

                JSONObject ticketJson = new JSONObject();

                ticketJson.put("ticketId", ticket.getTicketId());
                ticketJson.put("name", ticket.getFirstName() + " " + ticket.getLastName());
                ticketJson.put("personnelName", ticket.getPersnolFirstName() + " " + ticket.getPersnolLastName());
                ticketJson.put("nationalCode", ticket.getNationalCode());
                ticketJson.put("hospitalName", ticket.getHospital().getName());
                ticketJson.put("sectionName", ticket.getSection().getTitle());
                ticketJson.put("sendTypeTitle", ticket.getSendType().getTitle());
                ticketJson.put("submitDate", ticket.getSubmitDate());

                ticketArray.put(ticketJson);
            }
            return ticketArray.toString();
        } catch (Exception ex) {
            return "";
        }
    }

}
