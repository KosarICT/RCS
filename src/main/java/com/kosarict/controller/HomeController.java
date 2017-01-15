package com.kosarict.controller;

import com.kosarict.dao.*;
import com.kosarict.entity.*;
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
 * Created by Sadegh-Pc on 11/27/2016.
 */
@Controller
public class HomeController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TabDao tabDao;

    @Autowired
    private RelationDao relationDao;

    @Autowired
    private ComplainTypeDao complainTypeDao;

    @Autowired
    private ShiftDao shiftDao;

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private HospitalDao hospitalDao;

    @Autowired
    private ComplainantDao complainantDao;

    @Autowired
    private TicketDao ticketDao;


    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView model = new ModelAndView("home");
        model.addObject("relationLists", getMenu());

        return model;
    }

    @RequestMapping(value = "/home/api/getMenu", method = RequestMethod.GET)
    public
    @ResponseBody
    String getMenuData() {
        return getMenu().toString();
    }

    private JSONArray getMenu() {
        List<Tab> tabList = tabDao.getAllTabList();
        JSONArray userArray = new JSONArray();
        JSONArray tabArray = new JSONArray();
        JSONArray jsonArray = new JSONArray();

        JSONObject userObject = new JSONObject();
        userObject.put("displayName", getCurrentUser().getDisplayName());
        userObject.put("imageName", getCurrentUser().getImageName());

        userArray.put(userObject);
        jsonArray.put(userArray);

        for (Tab tab : tabList) {
            List<Tab> childTab = tabDao.getChildTabByParentId(tab.getTabId());

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("title", tab.getTitle());
            jsonObject.put("icon", tab.getIcon());
            jsonObject.put("url", tab.getUrl());
            jsonObject.put("child", childTab);

            tabArray.put(jsonObject);
        }

        jsonArray.put(tabArray);

        return jsonArray;
    }

    private Users getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = userDetails.getUsername();
        Users user = userDao.findUserByUserName(userName);

        return user;
    }

    @RequestMapping(value = "/admin/api/getTop10Ticket", method = RequestMethod.GET)
    public
    @ResponseBody
    String getTop10Ticket() {

        List<Ticket> ticketList = ticketDao.getTop10Ticket();

        JSONArray dataArray = new JSONArray();

        for (Ticket ticket : ticketList) {

            JSONObject dataItem = new JSONObject();

            dataItem.put("ticketTypeTitle", ticket.getTicketType().getTitle());
            dataItem.put("ticketSubject", ticket.getSubject());
            dataItem.put("sectionTitle", ticket.getSection().getTitle());
            dataItem.put("sendTypeTitle", ticket.getSendType().getTitle());
            dataItem.put("submitDate", ticket.getSubmitDate());
            dataItem.put("hospitalName", ticket.getHospital().getName());

            dataArray.put(dataItem);
        }

        return dataArray.toString();
    }

    private List<Relation> getRelationLists() {
        return relationDao.getAllRelationList();
    }

    private List<ComplaintType> getComplainTypeLists() {
        return complainTypeDao.getAllComplaintTypeList();
    }

    private List<Shift> getShiftLists() {
        return shiftDao.getAllShiftList();
    }

    private List<Section> getSectionLists() {
        return sectionDao.getAllSectionList();
    }

    private List<Hospital> getHospitalLists() {
        return hospitalDao.getAllHospitalList();
    }
}
