package com.kosarict.controller;

import com.kosarict.dao.HospitalDao;
import com.kosarict.dao.UserDao;
import com.kosarict.dao.UserSectionDao;
import com.kosarict.entity.Hospital;
import com.kosarict.entity.Users;
import com.kosarict.entity.UsersHospitalSection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Younes on 03/12/2016.
 */
@Controller
public class HospitalController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private HospitalDao hospitalDao;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private UserSectionDao userSectionDao;



    @RequestMapping(value = "/hospital", produces = "application/json")
    public ModelAndView hospital() {
        ModelAndView model = new ModelAndView("hospital");
        model.addObject("lists", getHospitalsList());
        return model;
    }

    @RequestMapping(value = "/hospital/api/getData", method = RequestMethod.GET)
    public
    @ResponseBody
    String getData() {
        try {
            JSONArray jsonArray = new JSONArray();

            for (Hospital hospital : getHospitalsList()) {

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("hospitalId", hospital.getHospitalId());
                jsonObject.put("name", hospital.getName());
                jsonObject.put("smsNumber", hospital.getSmsNumber());
                jsonObject.put("email", hospital.getEmail());
                jsonObject.put("url", hospital.getUrl());

                jsonArray.put(jsonObject);
            }

            return jsonArray.toString();
        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }

    @RequestMapping(value = "/hospital/api/saveHospital", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveHospital(@RequestBody String model) {
        try {
            JSONArray jsonArray = new JSONArray(model);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            int hospitalId = jsonObject.getInt("hospitalId");
            String hospitalName = jsonObject.getString("hospitalName");
            String smsNumber = jsonObject.getString("smsNumber");
            String email = jsonObject.getString("email");
            String url = jsonObject.getString("url");
            String address = jsonObject.getString("address");
            String description = jsonObject.getString("description");
            String imageName = jsonObject.getString("imageName");

            Hospital hospital;

            if(hospitalId <= 0){
                hospital = new Hospital();
            }else{
                hospital = hospitalDao.findHospitalById(hospitalId);
            }

            hospital.setName(hospitalName);
            hospital.setSmsNumber(smsNumber);
            hospital.setEmail(email);
            hospital.setUrl(url);
            hospital.setAddress(address);
            hospital.setDescription(description);
            hospital.setImageName(imageName);
            hospital.setEnable(true);

            int newHospitalId = hospitalDao.saveHospital(hospital);

            return String.valueOf(true);
        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }

    @RequestMapping(value = "/hospital/api/getHospitalForEdit", method = RequestMethod.POST)
    public
    @ResponseBody
    String getHospitalForEdit(@RequestBody String hospitalId) {
        try {
            JSONArray jsonArray = new JSONArray();
            int id = Integer.parseInt(hospitalId);

            Hospital hospital = hospitalDao.findHospitalById(id);

            if (hospital != null) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("hospitalId", hospital.getHospitalId());
                jsonObject.put("name", hospital.getName());
                jsonObject.put("smsNumber", hospital.getSmsNumber());
                jsonObject.put("email", hospital.getEmail());
                jsonObject.put("url", hospital.getUrl());
                jsonObject.put("address", hospital.getAddress());
                jsonObject.put("description", hospital.getDescription());
                jsonObject.put("imageName", hospital.getImageName());

                jsonArray.put(jsonObject);
            }

            return jsonArray.toString();
        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }

    @RequestMapping(value = "/hospital/api/deleteHospital", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteHospital(@RequestBody String hospitalId) {
        try {
            int id = Integer.parseInt(hospitalId);
            hospitalDao.deleteHospital(id);
            return String.valueOf(true);
        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }

    @RequestMapping(value = "/hospital/api/uploadImage", method = RequestMethod.POST)
    public
    @ResponseBody
    String doUpload(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();

            String uniqFileName = fileName;

            File rootDir = new File(servletContext.getRealPath("/static/hospitalImage"));

            if (!rootDir.exists())
                rootDir.mkdirs();


            if (!"".equalsIgnoreCase(fileName)) {
                file.transferTo(new File(rootDir.getAbsolutePath() + File.separator + uniqFileName));
            }

            return String.valueOf(true);
        } catch (IOException e) {
            return String.valueOf(false);
        }
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

    private List<Hospital> getHospitalsList() {
        Hospital hospital = hospitalDao.findHospitalById(getCurrentHospital());

        List<Hospital> hospitalList = new ArrayList<Hospital>();

        hospitalList.add(hospital);

        return hospitalList;

        //return hospitalDao.getAllHospitalList();
    }
}
