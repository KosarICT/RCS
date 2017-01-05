package com.kosarict.controller;

import com.kosarict.dao.HospitalDao;
import com.kosarict.entity.Hospital;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Younes on 03/12/2016.
 */
@Controller
public class HospitalController {
    @Autowired
    private HospitalDao hospitalDao;

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

    private List<Hospital> getHospitalsList() {
        return hospitalDao.getAllHospitalList();
    }
}
