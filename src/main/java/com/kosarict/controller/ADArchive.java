package com.kosarict.controller;

import com.kosarict.dao.TicketDao;
import com.kosarict.entity.Ticket;
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
 * Created by Younes on 19/01/2017.
 */
@Controller
public class ADArchive {

    @Autowired
    private TicketDao ticketDao;

    @RequestMapping(value = "/adArchive", method = RequestMethod.GET)
    public ModelAndView getArchiveView() {
        ModelAndView model = new ModelAndView("adArchive");

        return model;
    }
    @RequestMapping(value = "/archive/api/getArchiveList", method = RequestMethod.GET)
    public
    @ResponseBody
    String getArchiveList() {

        List<Ticket> ticketList = ticketDao.getTicketArchiveList();

        JSONArray dataArray = new JSONArray();

        for (Ticket ticket : ticketList) {

            JSONObject dataItem = new JSONObject();

            dataItem.put("ticketTypeTitle", ticket.getTicketType().getTitle());
            dataItem.put("ticketSubject", ticket.getSubject());
            dataItem.put("hospitalName", ticket.getHospital().getName());
            dataItem.put("sectionTitle", ticket.getSection().getTitle());
            dataItem.put("sendTypeTitle", ticket.getSendType().getTitle());
            dataItem.put("submitDate", ticket.getSubmitDate());
            dataItem.put("ticketId", ticket.getTicketId());

            dataArray.put(dataItem);
        }

        return dataArray.toString();
    }
}
