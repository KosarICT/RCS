package com.kosarict.controller;

import com.kosarict.dao.HospitalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Sadegh-Pc on 1/24/2017.
 */
@Controller
public class FollowController {

    @Autowired
    private HospitalDao hospitalDao;


    @RequestMapping(value = "/{hospitalId}/follow", method = RequestMethod.GET)
    public ModelAndView complain(@PathVariable(value = "hospitalId") String hospitalId) {
        ModelAndView model = new ModelAndView("/site/follow");
        model.addObject("hospitalId", hospitalId);
        model.addObject("hospitalImage", getHosptialName(Integer.parseInt(hospitalId)));

        return model;
    }

    private String getHosptialName(int hospitalId){
        return hospitalDao.findHospitalById(hospitalId).getImageName();
    }
}
