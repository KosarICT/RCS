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
 * Created by Admin on 12/5/2016.
 */
@Controller
public class DefaultController {

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




    @RequestMapping(value = "/{hospitalId}", method = RequestMethod.GET)
    public ModelAndView getViewPage(@PathVariable(value = "hospitalId") String hospitalId) {
        ModelAndView model = new ModelAndView("default");
        model.addObject("relationLists", getRelationLists());
        model.addObject("complainTypeLists", getComplainTypeLists());
        model.addObject("shiftLists", getShiftLists());
        model.addObject("sectionLists", getSectionLists());
        model.addObject("hospitalList", getHospitalLists());
        model.addObject("hospitalId", hospitalId);
        model.addObject("hospitalName", getHosptialName(Integer.parseInt(hospitalId)));

        return model;
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

    private String getHosptialName(int hospitalId){
        return hospitalDao.findHospitalById(hospitalId).getName();
    }

}