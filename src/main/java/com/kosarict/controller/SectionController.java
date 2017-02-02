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
 * Created by Sadegh-Pc on 2/2/2017.
 */
@Controller
public class SectionController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserSectionDao userSectionDao;

    @Autowired
    private HospitalSectionDao hospitalSectionDao;

    @Autowired
    private HospitalDao hospitalDao;

    @Autowired
    private SectionDao sectionDao;


    @RequestMapping(value = "/section", method = RequestMethod.GET)
    public ModelAndView section() {
        ModelAndView model = new ModelAndView("section");

        model.addObject("lists", getHospitalSectionByHospitalId());

        return model;
    }

    private Users getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = userDetails.getUsername();
        Users user = userDao.findUserByUserName(userName);

        return user;
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


    private List<Section> getHospitalSectionByHospitalId() {
        Users user = getCurrentUser();
        List<UsersHospitalSection> usersHospitalSectionList = userSectionDao.findUserHospitalSectionByUserId(user.getUserId());

        int hospitalId = 0;

        for (UsersHospitalSection usersHospitalSection : usersHospitalSectionList) {
            hospitalId = usersHospitalSection.getHospitalSection().getHospital().getHospitalId();
        }

        List<Section> sectionList = hospitalSectionDao.getSectionList(hospitalId);

        return sectionList;

/*        JSONArray jsonArray = new JSONArray();



        for (HospitalSection hospitalSectionListItem : hospitalSectionList){
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("sectionId", hospitalSectionListItem.getSection().getSectionId());
            jsonObject.put("title", hospitalSectionListItem.getSection().getTitle());
            jsonObject.put("description", hospitalSectionListItem.getSection().getDescription());

            jsonArray.put(jsonArray);
        }

        return jsonArray;*/
    }

    @RequestMapping(value = "/section/api/save", method = RequestMethod.POST)
    public
    @ResponseBody
    String save(@RequestBody String model) {

        try {
            JSONArray jsonArray = new JSONArray(model);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            int sectionId = jsonObject.getInt("sectionId");
            String title = jsonObject.getString("title");
            String description = jsonObject.getString("description");


            Section section;

            if (sectionId <= 0) {
                section = new Section();
                section.setSectionId(0);
            } else {
                section = sectionDao.findSectionById(sectionId);
            }


            section.setTitle(title);
            section.setDescription(description);
            section.setView(true);
            section.setEnable(true);

            int newSectionId = userSectionDao.saveSection(section);

            if (sectionId <= 0) {
                HospitalSection hospitalSection = new HospitalSection();

                Hospital hospital = hospitalDao.findHospitalById(getCurrentHospital());
                Section sectionItem = sectionDao.findSectionById(newSectionId);


                hospitalSection.setHospital(hospital);
                hospitalSection.setSection(sectionItem);
                hospitalSection.setEnable(true);

                hospitalSectionDao.saveHospitalSection(hospitalSection);
            }

            return String.valueOf(true);

        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }

    @RequestMapping(value = "/section/api/getSectionForEdit", method = RequestMethod.POST)
    public
    @ResponseBody
    String getSectionForEdit(@RequestBody String sectionId) {

        try {
            JSONArray jsonArray = new JSONArray();
            int id = Integer.parseInt(sectionId);

            Section section = sectionDao.findSectionById(id);


            //List<UserRole> rolesUser=userRoleDao.getUserRole(id);
            if (section != null) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("title", section.getTitle());
                jsonObject.put("description", section.getDescription());


                jsonArray.put(jsonObject);
            }

            return jsonArray.toString();
        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }
}
