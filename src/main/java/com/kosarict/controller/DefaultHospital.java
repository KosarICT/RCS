package com.kosarict.controller;

import com.kosarict.dao.HospitalDao;
import com.kosarict.entity.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Sadegh-Pc on 1/31/2017.
 */
@Controller
public class DefaultHospital {

    @Autowired
    private HospitalDao hospitalDao;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView viewPage() {

        ModelAndView model = new ModelAndView("/site/selectHospital");

        model.addObject("hospitalList", getHospitalLists());

        return model;
    }

    private List<Hospital> getHospitalLists() {
        return hospitalDao.getAllHospitalList();
    }
}
