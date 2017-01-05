package com.kosarict.controller;

import com.kosarict.dao.ADOfferDao;
import com.kosarict.dao.HospitalSectionDao;
import com.kosarict.dao.OfferAttachmentDao;
import com.kosarict.entity.HospitalSection;
import com.kosarict.entity.Offer;
import com.kosarict.entity.OfferAttachment;
import com.kosarict.model.Constant;
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
 * Created by Younes on 17/12/2016.
 */
@Controller
public class ADOfferController {
    @Autowired
    private ADOfferDao offerDao;

    @Autowired
    private HospitalSectionDao hospitalSectionDao;

    @Autowired
    private OfferAttachmentDao offerAttachmentDao;

    @RequestMapping(value = "/adOffer", method = RequestMethod.GET)
    public ModelAndView getAppreciationView() {
        ModelAndView model = new ModelAndView("adOffer");

        model.addObject("offerList", getOfferList());
        model.addObject("hospitalSectionList", getSectionList());
        return model;
    }

    @RequestMapping(value = "/adOffer/api/getAllOfferData", method = RequestMethod.GET)
    public
    @ResponseBody
    String getAllOfferData() {
        JSONArray jsonArray = new JSONArray();

        for (Offer offer : getOfferList()) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("offerId", offer.getOfferId());
            jsonObject.put("name", offer.getFirstName() + " " + offer.getLastName());
            jsonObject.put("nationalCode", offer.getNationalCode());
            jsonObject.put("mobile", offer.getMobile());
            jsonObject.put("hospitalName", offer.getHospital().getName());
            jsonObject.put("sectionName", offer.getSection().getTitle());
            jsonObject.put("email", offer.getEmail());
            jsonObject.put("trackingCode", offer.getTrackingCode());

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    @RequestMapping(value = "/adOffer/api/findOfferById", method = RequestMethod.POST)
    public
    @ResponseBody
    String findOfferById(@RequestBody String offerId) {
        long id = Long.parseLong(offerId);
        Offer offer = offerDao.findOfferById(id);

        List<OfferAttachment> offerAttachments = offerAttachmentDao.getOfferAttachmentListByOfferId(id);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("appreciationId", offer.getOfferId());
        jsonObject.put("name", offer.getFirstName() + " " + offer.getLastName());
        jsonObject.put("nationalCode", offer.getNationalCode());
        jsonObject.put("phoneNumber", offer.getPhoneNumber());
        jsonObject.put("mobile", offer.getMobile());
        jsonObject.put("subject", offer.getSubject());
        jsonObject.put("description", offer.getDescription());
        jsonObject.put("email", offer.getEmail());
        jsonObject.put("trackingCode", offer.getTrackingCode());
        jsonObject.put("sectionTitle", offer.getSection().getTitle());
        jsonObject.put("hospitalName", offer.getHospital().getName());
        jsonObject.put("attachList", offerAttachments);

        jsonArray.put(jsonObject);

        return jsonArray.toString();
    }

    private List<Offer> getOfferList() {
        return offerDao.getAllOfferList();
    }

    private List<HospitalSection> getSectionList() {
        return hospitalSectionDao.getHospitalSectionsListByHospitalId(Constant.hospitalId);
    }
}
