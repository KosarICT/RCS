package com.kosarict.controller;

import com.kosarict.dao.*;
import com.kosarict.entity.*;
import com.kosarict.tools.PersianCalendar;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by Younes on 04/12/2016.
 */
@Controller
public class ComplainController {

    @Autowired
    private ComplainDao complainDao;

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
    private SendTypeDao sendTypeDao;

    @Autowired
    private ComplainantRelationDao complainantRelationDao;

    @Autowired
    private ComplainAttachmentDao complainAttachmentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ComplainErrandDao complainErrandDao;



    @RequestMapping(value = "/complaint",method = RequestMethod.GET)
    public ModelAndView complain() {
        ModelAndView model = new ModelAndView("/site/complain");
        model.addObject("relationLists", getRelationLists());
        model.addObject("complainTypeLists", getComplainTypeLists());
        model.addObject("shiftLists", getShiftLists());
        model.addObject("sectionLists", getSectionLists());
        model.addObject("hospitalList", getHospitalLists());
        return model;
    }

    @RequestMapping(value = "/complaint/api/deleteComplain", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteComplain(@RequestBody String complainId) {
        try {
            int id = Integer.parseInt(complainId);
            complainDao.deleteComplain(id);
            return String.valueOf(true);
        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }

    @RequestMapping(value = "/complaint/api/saveComplain", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveComplain(@RequestBody String model) {
        try {
            JSONArray jsonArray = new JSONArray(model);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            long complainId = jsonObject.getLong("complainId");
            int hospitalId = jsonObject.getInt("hospitalId");
            int sectionId = jsonObject.getInt("sectionId");
            Integer shiftId = jsonObject.getInt("shiftId");

            Integer compainer = jsonObject.getInt("compainer");
            Integer relationId = jsonObject.getInt("relationId");
            String sickName = jsonObject.getString("sickName");
            String sickFamily = jsonObject.getString("sickFamily");
            String sickNationalCode = jsonObject.getString("sickNationalCode");
            String sickTel = jsonObject.getString("sickTel");
            String sickMobile = jsonObject.getString("sickMobile");
            Integer complaintTypeId = jsonObject.getInt("complaintTypeId");
            String complainSubject = jsonObject.getString("complainSubject");
            String complainDescription = jsonObject.getString("complainDescription");
            String complainEmail = jsonObject.getString("complainEmail");
            String fileName = jsonObject.getString("fileName");

            PersianCalendar persianCalendar = new PersianCalendar();
            String currentDate = persianCalendar.getIranianSimpleDate();

            Complain complain = new Complain();
            Hospital hospital = hospitalDao.findHospitalById(hospitalId);
            Section section = sectionDao.findSectionById(sectionId);
            Shift shift = shiftDao.findShiftById(shiftId.shortValue());
            ComplaintType complaintType = complainTypeDao.findComplaintTypeById(complaintTypeId.shortValue());
            Complainant complainant = complainantDao.findComplainantById(compainer.shortValue());
            SendType sendType = sendTypeDao.findSendTypeById((short) 1);

            Integer trackingNumber = trackingNumber();

            complain.setComplainId(complainId);
            complain.setHospital(hospital);
            complain.setSection(section);
            complain.setSendType(sendType);
            complain.setComplainant(complainant);
            complain.setShift(shift);
            complain.setFirstName(sickName);
            complain.setLastName(sickFamily);
            complain.setNationalCode(sickNationalCode);
            complain.setPhoneNumber(sickTel);
            complain.setMobile(sickMobile);
            complain.setComplaintType(complaintType);
            complain.setSubject(complainSubject);
            complain.setDescription(complainDescription);
            complain.setSubmitDate(currentDate);
            complain.setEmail(complainEmail);
            complain.setTrackingCode(trackingNumber.toString());
            complain.setEnable(true);

            long newComplainId = complainDao.saveComplain(complain);

            if (compainer == 2) {
                String compalainerName = jsonObject.getString("compalainerName");
                String compalainerFamily = jsonObject.getString("compalainerFamily");
                String registerNationalCode = jsonObject.getString("registerNationalCode");

                ComplainantRelation complainantRelation = new ComplainantRelation();

                complainantRelation.setRelationId(relationId.shortValue());
                complainantRelation.setComplainId(newComplainId);
                complainantRelation.setFirstName(compalainerName);
                complainantRelation.setLastName(compalainerFamily);
                complainantRelation.setNationalCode(registerNationalCode);

                complainantRelationDao.saveComplainantRelation(complainantRelation);
            }

            if (!fileName.matches("")) {

                ComplainAttachment complainAttachment = new ComplainAttachment();

                complainAttachment.setComplainId(newComplainId);
                complainAttachment.setFileName(fileName.split("\\.")[0]);
                complainAttachment.setFileType(fileName.split("\\.")[1]);
                complainAttachment.setDate(currentDate);

                complainAttachmentDao.saveComplainAttachment(complainAttachment);
            }


            List<UsersHospitalSection> userSections = complainDao.forwardComplain(hospitalId, sectionId);
            if (userSections.size() > 0) {
                UsersHospitalSection userSection = userSections.get(0);
                Users users = userDao.findUserById(userSection.getUser().getUserId());
                ComplainErrand complainErrand = new ComplainErrand();
                Complain complain1 = complainDao.findComplainById(newComplainId);

                complainErrand.setComplain(complain1);
                complainErrand.setAssignedUser(users);
                complainErrand.setSubmitDate(currentDate);

                complainErrandDao.saveComplainErrand(complainErrand);
            }

            return trackingNumber.toString();

        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }

    @RequestMapping(value = "/complaint/api/uploadAttachment",method = RequestMethod.POST)
    public @ResponseBody String uploadAttachment(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();

            String uniqFileName = fileName;

            File rootDir = new File(servletContext.getRealPath("/static/attachment"));

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

    private int trackingNumber() {
        Integer trackingNumber = 0;
        while (true) {
            Random rand = new Random();

            trackingNumber = rand.nextInt((1000000 - 100000) + 1) + 100000;

            if (complainDao.trackingCodeIsExist(trackingNumber.toString())) {
                continue;
            }

            break;
        }
        return trackingNumber;
    }
}
