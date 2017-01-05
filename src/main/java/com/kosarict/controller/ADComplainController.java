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
    private ComplainDao complainDao;

    @Autowired
    private UserSectionDao userSectionDao;

    @Autowired
    private ComplainErrandDao complainErrandDao;

    @Autowired
    private HospitalSectionDao hospitalSectionDao;

    @Autowired
    private ComplainAttachmentDao complainAttachmentDao;


    @RequestMapping(value = "/adComplain", method = RequestMethod.GET)
    public ModelAndView getComplainView() {
        ModelAndView model = new ModelAndView("adComplain");
        model.addObject("lists", getComplainList());
        model.addObject("hospitalSectionList", getSectionList());

        return model;
    }

    @RequestMapping(value = "/adComplain/api/getData", method = RequestMethod.GET)
    public
    @ResponseBody
    String getData() {
        JSONArray jsonArray = new JSONArray();

        for (ComplainErrand complainErrand : getComplainList()) {
            Complain complain = complainErrand.getComplain();

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("complainId", complain.getComplainId());
            jsonObject.put("complainantTitle", complain.getComplainant().getTitle());
            jsonObject.put("name", complain.getFirstName() + " " + complain.getLastName());
            jsonObject.put("nationalCode", complain.getNationalCode());
            jsonObject.put("hospitalName", complain.getHospital().getName());
            jsonObject.put("complaintTypeTitle", complain.getComplaintType().getTitle());
            jsonObject.put("submitDate", complain.getSubmitDate());
            jsonObject.put("sendTypeTitle", complain.getSendType().getTitle());

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    @RequestMapping(value = "/adComplain/api/findComplainById", method = RequestMethod.POST)
    public
    @ResponseBody
    String findComplainById(@RequestBody String complainId) {
        long id = Long.parseLong(complainId);

        Complain complain = complainDao.findComplainById(id);
        List<ComplainAttachment> complainAttachmentList = complainAttachmentDao.getComplainAttachmentListByComplainId(id);

        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("complainId", complain.getComplainId());
        jsonObject.put("name", complain.getFirstName() + " " + complain.getLastName());
        jsonObject.put("nationalCode", complain.getNationalCode());
        jsonObject.put("phoneNumber", complain.getPhoneNumber());
        jsonObject.put("mobile", complain.getMobile());
        jsonObject.put("tel", complain.getPhoneNumber());
        jsonObject.put("subject", complain.getSubject());
        jsonObject.put("description", complain.getDescription());
        jsonObject.put("submitDate", complain.getSubmitDate());
        jsonObject.put("email", complain.getEmail());
        jsonObject.put("complaintTypeTitle", complain.getComplaintType().getTitle());
        jsonObject.put("shiftTitle", complain.getShift().getTitle());
        jsonObject.put("sectionTitle", complain.getSection().getTitle());
        jsonObject.put("sendTypeTitle", complain.getSendType().getTitle());
        jsonObject.put("complainantTitle", complain.getComplainant().getTitle());
        jsonObject.put("attachList", complainAttachmentList);

        jsonArray.put(jsonObject);

        return jsonArray.toString();
    }

    @RequestMapping(value = "/adComplain/api/saveComplainErrand", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveComplainErrand(@RequestBody String model) {
        try {
            JSONArray jsonArray = new JSONArray(model);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            long complainId = jsonObject.getLong("complainId");
            int userId = jsonObject.getInt("userId");
            String description = jsonObject.getString("description");

            PersianCalendar persianCalendar = new PersianCalendar();
            String currentDate = persianCalendar.getIranianSimpleDate();

            Complain complain = complainDao.findComplainById(complainId);
            Users user = userDao.findUserById(userId);

            ComplainErrand complainErrand = new ComplainErrand();

            complainErrand.setComplainErrandId(0);
            complainErrand.setComplain(complain);
            complainErrand.setCreateUser(getCurrentUser());
            complainErrand.setAssignedUser(user);
            complainErrand.setSubmitDate(currentDate);
            complainErrand.setView(false);
            complainErrand.setDescription(description);

            complainErrandDao.saveComplainErrand(complainErrand);

            return String.valueOf(true);

        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }

    @RequestMapping(value = "/adComplain/api/getUserOfSection", method = RequestMethod.POST)
    public
    @ResponseBody
    String getUserOfSection(@RequestBody String sectionId) {
        int id = Integer.parseInt(sectionId);

        List<UsersHospitalSection> userSectionList = userSectionDao.getUserSectionBySectionId(id);

        JSONArray jsonArray = new JSONArray();

        for (UsersHospitalSection userSection : userSectionList) {
            JSONObject jsonObject = new JSONObject();

            Users user = userDao.findUserById(userSection.getUser().getUserId());

            jsonObject.put("userId", user.getUserId());
            jsonObject.put("name", user.getFirstName() + " " + user.getLastName());

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    private List<ComplainErrand> getComplainList() {
        return complainDao.getComplainListByUserId(getCurrentUser().getUserId());
    }

    private List<HospitalSection> getSectionList() {
        return hospitalSectionDao.getHospitalSectionsListByHospitalId(Constant.hospitalId);
    }

    private Users getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = userDetails.getUsername();
        Users user = userDao.findUserByUserName(userName);

        return user;
    }
}
