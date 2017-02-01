package com.kosarict.controller;

import com.kosarict.dao.HospitalDao;
import com.kosarict.dao.HospitalSectionDao;
import com.kosarict.dao.SectionDao;
import com.kosarict.entity.Hospital;
import com.kosarict.entity.HospitalSection;
import com.kosarict.entity.Section;
import com.kosarict.model.HospitalSectionModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Younes on 11/12/2016.
 */
@Controller
public class HospitalSectionController {
    @Autowired
    private HospitalSectionDao hospitalSectionDao;

    @Autowired
    private HospitalDao hospitalDao;
    @Autowired
    private SectionDao sectionDao;


    @RequestMapping(value = "/hospitalSection", method = RequestMethod.GET)
    public ModelAndView getComplainView() {
        ModelAndView model = new ModelAndView("hospitalSection");
        model.addObject("hospitalList", getHospitalList());
        model.addObject("sectionList", getSectionList());
        model.addObject("hospitalSectionList", getHospitalSectionData());

        return model;
    }

    @RequestMapping(value = "/hospitalSection/api/saveHospitalSection", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveHospitalSection(@RequestBody String model) {
        try {
            JSONArray jsonArray = new JSONArray(model);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            long hospitalId = jsonObject.getLong("hospitalId");

            JSONArray hospitalSectionArray = jsonObject.getJSONArray("hospitalSectionArray");
            List<HospitalSection> hospitalSectionList = hospitalSectionDao.getHospitalSectionsListByHospitalId((int) hospitalId);

            for (HospitalSection hospitalSection : hospitalSectionList) {

                int index = 0;
                int oldCount = hospitalSectionArray.length();
                for (; index < hospitalSectionArray.length(); index++) {
                    JSONObject userRoleJsonObject = hospitalSectionArray.getJSONObject(index);

                    int sectionId = userRoleJsonObject.getInt("sectionId");

                    if (sectionId == hospitalSection.getSection().getSectionId()) {
                        hospitalSectionArray.remove(index);
                        break;
                    }
                }
                if (index == oldCount) {
                    hospitalSectionDao.deleteHospitalSectionBySectionId(hospitalSection.getHospitalSectionId());
                }
            }

            for (int j = 0; j < hospitalSectionArray.length(); j++) {
                JSONObject hospitalSectionArrayJSONObject = hospitalSectionArray.getJSONObject(j);

                int sectionId = hospitalSectionArrayJSONObject.getInt("sectionId");

                HospitalSection hospitalSection = new HospitalSection();
                Hospital hospital = hospitalDao.findHospitalById((int) hospitalId);
                Section section = sectionDao.findSectionById(sectionId);

                hospitalSection.setHospital(hospital);
                hospitalSection.setSection(section);
                hospitalSection.setEnable(true);

                hospitalSectionDao.saveHospitalSection(hospitalSection);
            }
            return String.valueOf(true);

        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }

    @RequestMapping(value = "/hospitalSection/api/getHospitalSectionData", method = RequestMethod.GET)
    public
    @ResponseBody
    String getData() {
        return getHospitalSectionData().toString();
    }

    @RequestMapping(value = "/hospitalSection/api/getHospitalSectionDataByHospitalId", method = RequestMethod.POST)
    public
    @ResponseBody
    List<HospitalSection> getHospitalSectionByHospitalId(@RequestBody String hospitalId) {
        int id = Integer.parseInt(hospitalId);
        return hospitalSectionDao.getHospitalSectionsListByHospitalId(id);
    }

    @RequestMapping(value = "/hospitalSection/api/findHospitalSectionByHospitalId", method = RequestMethod.POST)
    public
    @ResponseBody
    String findHospitalSectionByHospitalId(@RequestBody String hospitalId) {
        int id = Integer.parseInt(hospitalId);
        JSONArray jsonArray = new JSONArray();

        Hospital hospital = hospitalDao.findHospitalById(id);


        for (HospitalSection hospitalSection : hospitalSectionDao.getHospitalSectionsListByHospitalId(id)) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("sectionId", hospitalSection.getSection().getSectionId());

            jsonArray.put(jsonObject);

        }

        return jsonArray.toString();
    }

    private List<Hospital> getHospitalList() {
        return hospitalDao.getAllHospitalList();
    }

    private List<Section> getSectionList() {
        return sectionDao.getAllSectionList();
    }

    private List<HospitalSectionModel> getHospitalSectionData() {
        List<HospitalSectionModel> hospitalSectionModelList = new ArrayList<HospitalSectionModel>();

        for (Hospital hospital : getHospitalList()) {
            int hospitalId = hospital.getHospitalId();
            String section = "";

            List<HospitalSection> hospitalSectionList = hospitalSectionDao.getHospitalSectionsListByHospitalId(hospitalId);
            if (hospitalSectionList.size() > 0) {

                for (HospitalSection hospitalSection : hospitalSectionList) {
                    section = section + hospitalSection.getSection().getTitle() + " - ";
                }
            }

            HospitalSectionModel hospitalSectionModel = new HospitalSectionModel();
            hospitalSectionModel.setHospitalId(hospitalId);
            hospitalSectionModel.setHospitalName(hospital.getName());
            if (!section.matches(""))
                hospitalSectionModel.setSectionName(section.substring(0, section.lastIndexOf(" - ")));
            else {
                hospitalSectionModel.setSectionName("");
            }

            hospitalSectionModelList.add(hospitalSectionModel);
        }

        return hospitalSectionModelList;
    }

}
