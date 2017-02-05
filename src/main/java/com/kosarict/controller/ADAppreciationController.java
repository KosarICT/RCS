package com.kosarict.controller;

import com.kosarict.dao.HospitalSectionDao;
import com.kosarict.dao.TicketDao;
import com.kosarict.dao.UserDao;
import com.kosarict.dao.UserSectionDao;
import com.kosarict.entity.HospitalSection;
import com.kosarict.entity.Users;
import com.kosarict.entity.UsersHospitalSection;
import com.kosarict.model.Constant;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserSectionDao userSectionDao;

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
        int hospitalId =getCurrentHospital();
        return hospitalSectionDao.getHospitalSectionsListByHospitalId(hospitalId);
    }

    private int getCurrentHospital() {
        Users user = getCurrentUser();
        List<UsersHospitalSection> usersHospitalSectionList = userSectionDao.findUserHospitalSectionByUserId(user.getUserId());

        int hospitalId = 0;

        for (UsersHospitalSection usersHospitalSection : usersHospitalSectionList) {
            hospitalId = usersHospitalSection.getHospitalSection().getHospital().getHospitalId();
        }

        return hospitalId;
    }

    private Users getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = userDetails.getUsername();
        Users user = userDao.findUserByUserName(userName);

        return user;
    }

}
