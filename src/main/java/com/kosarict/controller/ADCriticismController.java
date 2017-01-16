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

        return model;
    }

}
