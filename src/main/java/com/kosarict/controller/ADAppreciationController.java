package com.kosarict.controller;
import com.kosarict.dao.ADAppreciationDao;
import com.kosarict.dao.AppreciationAttachmentDao;
import com.kosarict.dao.HospitalSectionDao;
import com.kosarict.entity.Appreciation;
import com.kosarict.entity.AppreciationAttachment;
import com.kosarict.entity.HospitalSection;
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
 * Created by Younes on 16/12/2016.
 */
@Controller
public class ADAppreciationController {
    @Autowired
    private ADAppreciationDao appreciationDao;

    @Autowired
    private HospitalSectionDao hospitalSectionDao;

    @Autowired
    private AppreciationAttachmentDao appreciationAttachmentDao;

    @RequestMapping(value = "/adAppreciation", method = RequestMethod.GET)
    public ModelAndView getAppreciationView() {
        ModelAndView model = new ModelAndView("adAppreciation");

        model.addObject("appreciationListList", getAppreciationList());
        model.addObject("hospitalSectionList", getSectionList());
        return model;
    }

    @RequestMapping(value = "/adAppreciation/api/getAllAppreciationData", method = RequestMethod.GET)
    public
    @ResponseBody
    String getAllAppreciationData() {
        JSONArray jsonArray = new JSONArray();

        for (Appreciation appreciation : getAppreciationList()) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("appreciationId", appreciation.getAppreciationId());
            jsonObject.put("name", appreciation.getFirstName() + " " + appreciation.getLastName());
            jsonObject.put("nationalCode", appreciation.getNationalCode());
            jsonObject.put("rating", appreciation.getRaiting());
            jsonObject.put("hospitalName", appreciation.getHospital().getName());
            jsonObject.put("sectionName", appreciation.getSection().getTitle());
            jsonObject.put("personnelName", appreciation.getPersnolFirstName() + " " + appreciation.getPersnolLastName());


            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    @RequestMapping(value = "/adAppreciation/api/findAppreciationById", method = RequestMethod.POST)
    public
    @ResponseBody
    String findComplainById(@RequestBody String complainId) {
        long id = Long.parseLong(complainId);
        Appreciation appreciation = appreciationDao.findAppreciationById(id);

        List<AppreciationAttachment> appreciationAttachmentList = appreciationAttachmentDao.getAppreciationAttachmentListByAppreciationId(id);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("appreciationId", appreciation.getAppreciationId());
        jsonObject.put("name", appreciation.getFirstName() + " " + appreciation.getLastName());
        jsonObject.put("personnelName", appreciation.getPersnolFirstName() + " " + appreciation.getPersnolLastName());
        jsonObject.put("rating", appreciation.getRaiting());
        jsonObject.put("nationalCode", appreciation.getNationalCode());
        jsonObject.put("phoneNumber", appreciation.getPhoneNumber());
        jsonObject.put("mobile", appreciation.getMobile());
        jsonObject.put("subject", appreciation.getSubject());
        jsonObject.put("description", appreciation.getDescription());
        jsonObject.put("email", appreciation.getEmail());
        jsonObject.put("sectionTitle", appreciation.getSection().getTitle());
        jsonObject.put("hospitalName", appreciation.getHospital().getName());
        jsonObject.put("attachList", appreciationAttachmentList);

        jsonArray.put(jsonObject);

        return jsonArray.toString();
    }

    private List<Appreciation> getAppreciationList() {
        return appreciationDao.getAllAppreciationList();
    }

    private List<HospitalSection> getSectionList() {
        return hospitalSectionDao.getHospitalSectionsListByHospitalId(Constant.hospitalId);
    }


}
