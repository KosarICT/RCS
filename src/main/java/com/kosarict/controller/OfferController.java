package com.kosarict.controller;

import com.kosarict.model.Constant;
import com.kosarict.dao.*;
import com.kosarict.entity.*;
import com.kosarict.tools.PersianCalendar;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Random;
import java.io.File;
import java.io.IOException;

import static java.util.UUID.randomUUID;

/**
 * Created by Ali-Pc on 12/14/2016.
 */
@Controller
public class OfferController {

    @Autowired
    private HospitalDao hospitalDao;

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private OfferDao offerDao;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private OfferAttachmentDao offerAttachmentDao;

    @Autowired
    private ShiftDao shiftDao;

    @RequestMapping(value = "/{hospitalId}/offer", method = RequestMethod.GET)
    public ModelAndView complain(@PathVariable(value = "hospitalId") String hospitalId) {
        ModelAndView model = new ModelAndView("/site/offer");
        model.addObject("hospitalList", getHospitalLists());
        model.addObject("sectionLists", getSectionLists());
        model.addObject("hospitalId", hospitalId);
        return model;
    }

    private List<Hospital> getHospitalLists() {
        return hospitalDao.getAllHospitalList();
    }

    private List<Section> getSectionLists() {
        return sectionDao.getAllSectionList();
    }

    @RequestMapping(value = "/offer/api/saveOffer", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveOffer(@RequestBody String model) {
        try {

            JSONArray jsonArray = new JSONArray(model);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            long offerId = jsonObject.getLong("offerId");
            int hospitalId = jsonObject.getInt("hospitalId");
            int sectionId = jsonObject.getInt("sectionId");

            String name = jsonObject.getString("name");
            String family = jsonObject.getString("family");
            String nationalCode = jsonObject.getString("nationalCode");
            String tel = jsonObject.getString("tel");
            String mobile = jsonObject.getString("mobile");
            String offerSubject = jsonObject.getString("offerSubject");
            String offerDescription = jsonObject.getString("offerDescription");
            String email = jsonObject.getString("email");
            String fileName = jsonObject.getString("fileName");
            PersianCalendar persianCalendar = new PersianCalendar();
            String currentDate = persianCalendar.getIranianSimpleDate();

            Offer offer = new Offer();
            Hospital hospital = hospitalDao.findHospitalById(hospitalId);
            Section section = sectionDao.findSectionById(sectionId);

            offer.setOfferId(offerId);
            offer.setFirstName(name);
            offer.setLastName(family);
            offer.setNationalCode(nationalCode);
            offer.setPhoneNumber(tel);
            offer.setMobile(mobile);
            offer.setSubject(offerSubject);
            offer.setDescription(offerDescription);
            offer.setEmail(email);
            offer.setHospital(hospital);
            offer.setSection(section);
            offer.setEnable(true);
            offer.setSubmitDate(currentDate);
            offer.setSendTypeId(Constant.SendTypeSite);

            long newOfferId = offerDao.saveOffer(offer);


            if (!fileName.matches("")) {
                OfferAttachment offerAttachment = new OfferAttachment();
                offerAttachment.setFileName(fileName.split("\\.")[0]);
                offerAttachment.setDate(currentDate);
                offerAttachment.setFileType(fileName.split("\\.")[1]);
                offerAttachment.setOfferId(newOfferId);

                offerAttachmentDao.saveOfferAttachment(offerAttachment);
            }

            return String.valueOf(true);

        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }

    @RequestMapping(value = "/offer/api/uploadAttachment", method = RequestMethod.POST)
    public
    @ResponseBody
    String uploadAttachment(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();

            String uniqFileName = randomUUID() + fileName;

            File rootDir = new File(servletContext.getRealPath("/static/attachment"));

            if (!rootDir.exists())
                rootDir.mkdirs();


            if (!"".equalsIgnoreCase(fileName)) {
                file.transferTo(new File(rootDir.getAbsolutePath() + File.separator + uniqFileName));
            }

            return uniqFileName;
        } catch (IOException e) {
            return String.valueOf(false);
        }
    }

    @RequestMapping(value = "/offer/api/getSection", method = RequestMethod.POST)
    public
    @ResponseBody
    String getSection(@RequestBody String hospitalId) {
        try {
            int id = Integer.parseInt(hospitalId);
            JSONArray mainArray = new JSONArray();
            JSONObject mainObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            List<Section> sectionList = sectionDao.getSections(id);
            List<Shift> shiftList=shiftDao.getShifitByHospitalId(id);

//            /*for (Section section : sectionList) {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("sectionId", section.getSectionId());
//                jsonObject.put("title", section.getTitle());
//
//                jsonArray.put(jsonObject);
//            }*/

            mainObject.put("sections",sectionList);
            mainObject.put("shifits",shiftList);
            jsonArray.put(mainObject);
            return jsonArray.toString();
        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }

    private int TrackingNumber() {
        Integer trackingNumber = 0;
        while (true) {
            Random rand = new Random();

            trackingNumber = rand.nextInt((1000000 - 100000) + 1) + 100000;

            if (offerDao.trackingCodeIsExist(trackingNumber.toString())) {
                continue;
            }

            break;
        }
        return trackingNumber;
    }
}

