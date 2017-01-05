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
    private UserDao userDao;

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

        for (Criticize criticize : getCriticismList()) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("criticizeId", criticize.getCriticizeId());
            jsonObject.put("name", criticize.getFirstName() + " " + criticize.getLastName());
            jsonObject.put("nationalCode", criticize.getNationalCode());
            jsonObject.put("subject", criticize.getSubject());
            jsonObject.put("hospitalName", criticize.getHospital().getName());
            jsonObject.put("sectionTitle", criticize.getSection().getTitle());
            jsonObject.put("submitDate", criticize.getSubmitDate());
            jsonObject.put("sendTypeTitle", criticize.getSendType().getTitle());
            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    @RequestMapping(value = "/adCriticism/api/findCriticismById", method = RequestMethod.POST)
    public
    @ResponseBody
    String findCriticismById(@RequestBody String complainId) {
        long id = Long.parseLong(complainId);

        Criticize criticize = criticismDao.findCriticizeById(id);
        List<CriticizeAttachment> criticizeAttachmentList = criticizeAttachmentDao.getCriticizeAttachmentListByCriticizeId(id);

        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("criticizeId", criticize.getCriticizeId());
        jsonObject.put("name", criticize.getFirstName() + " " + criticize.getLastName());
        jsonObject.put("nationalCode", criticize.getNationalCode());
        jsonObject.put("phoneNumber", criticize.getPhoneNumber());
        jsonObject.put("mobile", criticize.getMobile());
        jsonObject.put("tel", criticize.getPhoneNumber());
        jsonObject.put("subject", criticize.getSubject());
        jsonObject.put("description", criticize.getDescription());
        jsonObject.put("submitDate", criticize.getSubmitDate());
        jsonObject.put("email", criticize.getEmail());
        jsonObject.put("trackingCode", criticize.getTrackingCode());
        jsonObject.put("sectionTitle", criticize.getSection().getTitle());
        jsonObject.put("hospitalName", criticize.getHospital().getName());
        jsonObject.put("attachList", criticizeAttachmentList);

        jsonArray.put(jsonObject);

        return jsonArray.toString();
    }

//    @RequestMapping(value = "/adCriticism/api/saveCriticismErrand", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    String saveCriticismErrand(@RequestBody String model) {
//        try {
//            JSONArray jsonArray = new JSONArray(model);
//            JSONObject jsonObject = jsonArray.getJSONObject(0);
//
//            long criticizeId = jsonObject.getLong("criticizeId");
//            int userId = jsonObject.getInt("userId");
//            String description = jsonObject.getString("description");
//
//            PersianCalendar persianCalendar = new PersianCalendar();
//            String currentDate = persianCalendar.getIranianSimpleDate();
//
//            Criticize criticize = criticismDao.findCriticizeById(criticizeId);
//            Users user = userDao.findUserById(userId);
//
//            Criti complainErrand = new ComplainErrand();
//
//            complainErrand.setComplainErrandId(0);
//            complainErrand.setComplain(criticize);
//            complainErrand.setCreateUser(getCurrentUser());
//            complainErrand.setAssignedUser(user);
//            complainErrand.setSubmitDate(currentDate);
//            complainErrand.setView(false);
//            complainErrand.setDescription(description);
//
//            complainErrandDao.saveComplainErrand(complainErrand);
//
//            return String.valueOf(true);
//
//        } catch (Exception ex) {
//            return String.valueOf(false);
//        }
//    }
//
//    @RequestMapping(value = "/adCriticism/api/getUserOfSection", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    String getUserOfSection(@RequestBody String sectionId) {
//        int id = Integer.parseInt(sectionId);
//
//        List<UsersHospitalSection> userSectionList = userSectionDao.getUserSectionBySectionId(id);
//
//        JSONArray jsonArray = new JSONArray();
//
//        for (UsersHospitalSection userSection : userSectionList) {
//            JSONObject jsonObject = new JSONObject();
//
//            Users user = userDao.findUserById(userSection.getUser().getUserId());
//
//            jsonObject.put("userId", user.getUserId());
//            jsonObject.put("name", user.getFirstName() + " " + user.getLastName());
//
//            jsonArray.put(jsonObject);
//        }
//
//        return jsonArray.toString();
//    }

    private List<Criticize> getCriticismList() {
        return criticismDao.getAllCriticizeList();
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
