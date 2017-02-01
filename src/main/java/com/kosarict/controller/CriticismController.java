package com.kosarict.controller;

import com.kosarict.dao.*;
import com.kosarict.entity.*;
import com.kosarict.tools.PersianCalendar;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 12/14/2016.
 */
@Controller
public class CriticismController {
    @Autowired
    private ShiftDao shiftDao;

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private HospitalDao hospitalDao;

    @Autowired
    private CriticismsDao criticismsDao;

    @Autowired
    private CriticizeAttachmentDao criticizeAttachmentDao;




    @RequestMapping(value = "/{hospitalId}/criticisms", method = RequestMethod.GET)
    public ModelAndView complain(@PathVariable(value = "hospitalId") String hospitalId) {
        ModelAndView model = new ModelAndView("/site/criticisms");

        model.addObject("shiftLists", getShiftLists());
        model.addObject("sectionLists", getSectionLists());
        model.addObject("hospitalList", getHospitalLists());
        model.addObject("hospitalId", hospitalId);
        return model;
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

    private int trackingNumber() {
        Integer trackingNumber = 0;
        while (true) {
            Random rand = new Random();

            trackingNumber = rand.nextInt((1000000 - 100000) + 1) + 100000;

            if (criticismsDao.trackingCodeIsExist(trackingNumber.toString())) {
                continue;
            }

            break;
        }
        return trackingNumber;
    }


    @RequestMapping(value = "/criticisms/api/saveCriticisms", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveCriticisms(@RequestBody String model) {
        try {
            JSONArray jsonArray = new JSONArray(model);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            long criticizeId = jsonObject.getLong("criticizeId");
            int hospitalId = jsonObject.getInt("hospitalId");
            int sectionId = jsonObject.getInt("sectionId");
            Integer shiftId = jsonObject.getInt("shiftId");

            String criticismName = jsonObject.getString("criticismName");
            String criticismFamily = jsonObject.getString("criticismFamily");
            String nationalCodeCriticism = jsonObject.getString("nationalCodeCriticism");
            String telCriticism = jsonObject.getString("telCriticism");
            String mobileCriticism = jsonObject.getString("mobileCriticism");
            String criticismSubject = jsonObject.getString("criticismSubject");
            String criticismDescription = jsonObject.getString("criticismDescription");
            String criticismEmail = jsonObject.getString("criticismEmail");
            String fileName = jsonObject.getString("fileName");

            PersianCalendar persianCalendar = new PersianCalendar();
            String currentDate = persianCalendar.getIranianSimpleDate();

            Criticize criticize = new Criticize();
            Hospital hospital = hospitalDao.findHospitalById(hospitalId);
            Section section = sectionDao.findSectionById(sectionId);
            Shift shift = shiftDao.findShiftById(shiftId.shortValue());

            Integer trackingNumber = trackingNumber();

            criticize.setCriticizeId(criticizeId);
            criticize.setHospital(hospital);
            criticize.setSection(section);
            criticize.setFirstName(criticismName);
            criticize.setLastName(criticismFamily);
            criticize.setNationalCode(nationalCodeCriticism);
            criticize.setPhoneNumber(telCriticism);
            criticize.setMobile(mobileCriticism);
            criticize.setSubject(criticismSubject);
            criticize.setDescription(criticismDescription);
            criticize.setEmail(criticismEmail);
            criticize.setTrackingCode(trackingNumber.toString());
            criticize.setEnable(true);

             long newCriticizeId = criticismsDao.saveCriticize(criticize);

            if (!fileName.matches("")) {

                CriticizeAttachment criticizeAttachment = new CriticizeAttachment();

                criticizeAttachment.setCriticizeId(criticizeId);
                criticizeAttachment.setFileName(fileName.split("\\.")[0]);
                criticizeAttachment.setFileType(fileName.split("\\.")[1]);
                criticizeAttachment.setDate(currentDate);

                criticizeAttachmentDao.saveCriticizeAttachment(criticizeAttachment);
            }

            return trackingNumber.toString();
        }catch (Exception ex) {
            return String.valueOf(false);
        }
    }


}
