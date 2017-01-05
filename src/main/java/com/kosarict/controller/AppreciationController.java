package com.kosarict.controller;

import com.kosarict.dao.*;
import com.kosarict.entity.*;
import com.kosarict.tools.PersianCalendar;
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
import java.util.Random;

/**
 * Created by Admin on 12/16/2016.
 */

@Controller
public class AppreciationController {

    @Autowired
    private ShiftDao shiftDao;

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private HospitalDao hospitalDao;

    @Autowired
    private AppreciationDao appreciationDao;

    @Autowired
    private AppreciationAttachmentDao appreciationAttachmentDao;


    @RequestMapping(value = "/appreciation", method = RequestMethod.GET)
    public ModelAndView complain() {
        ModelAndView model = new ModelAndView("/site/appreciation");
        model.addObject("shiftLists", getShiftLists());
        model.addObject("sectionLists", getSectionLists());
        model.addObject("hospitalList", getHospitalLists());
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

            if (appreciationDao.trackingCodeIsExist(trackingNumber.toString())) {
                continue;
            }

            break;
        }
        return trackingNumber;
    }

    @RequestMapping(value = "/appreciation/api/saveAppreciation", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveAppreciation(@RequestBody String model) {
        try {
            JSONArray jsonArray = new JSONArray(model);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            long appreciationId = jsonObject.getLong("appreciationId");
            int hospitalId = jsonObject.getInt("hospitalId");
            int sectionId = jsonObject.getInt("sectionId");
            Integer shiftId = jsonObject.getInt("shiftId");

            String appreciationName = jsonObject.getString("appreciationName");
            String appreciationFamily = jsonObject.getString("appreciationFamily");
            String nationalCodeAppreciation = jsonObject.getString("nationalCodeAppreciation");
            String telAppreciation = jsonObject.getString("telAppreciation");
            String mobileAppreciation = jsonObject.getString("mobileAppreciation");
            String appreciationSubject = jsonObject.getString("appreciationSubject");
            String appreciationDescription = jsonObject.getString("appreciationDescription");
            String appreciationEmail = jsonObject.getString("appreciationEmail");

            String appreciationUserName = jsonObject.getString("appreciationUserName");
            String appreciationUserFamily = jsonObject.getString("appreciationUserFamily");
            String fileName = jsonObject.getString("fileName");

            Appreciation appreciation = new Appreciation();
            Hospital hospital = hospitalDao.findHospitalById(hospitalId);
            Section section = sectionDao.findSectionById(sectionId);
            Shift shift = shiftDao.findShiftById(shiftId.shortValue());

            PersianCalendar persianCalendar = new PersianCalendar();
            String currentDate = persianCalendar.getIranianSimpleDate();

            Integer trackingNumber = trackingNumber();

            appreciation.setAppreciationId(appreciationId);
            appreciation.setHospital(hospital);
            appreciation.setSection(section);
            appreciation.setFirstName(appreciationName);
            appreciation.setLastName(appreciationFamily);
            appreciation.setNationalCode(nationalCodeAppreciation);
            appreciation.setPhoneNumber(telAppreciation);
            appreciation.setMobile(mobileAppreciation);
            appreciation.setSubject(appreciationSubject);
            appreciation.setDescription(appreciationDescription);
            appreciation.setEmail(appreciationEmail);
            appreciation.setTrackingCode(trackingNumber.toString());
            appreciation.setPersnolFirstName(appreciationUserName);
            appreciation.setPersnolLastName(appreciationUserFamily);
            appreciation.setView(false);
            appreciation.setEnable(true);
            appreciation.setRaiting(0);
            appreciation.setSubmitDate(currentDate);

            long newAppreciationId = appreciationDao.saveAppreciation(appreciation);

            if (!fileName.matches("")) {
                AppreciationAttachment appreciationAttachment = new AppreciationAttachment();

                appreciationAttachment.setFileName(fileName.split("\\.")[0]);
                appreciationAttachment.setDate(currentDate);
                appreciationAttachment.setFileType(fileName.split("\\.")[1]);
                appreciationAttachment.setAppreciationId(newAppreciationId);

                appreciationAttachmentDao.saveAppreciationAttachment(appreciationAttachment);
            }

            return trackingNumber.toString();


        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }
}
