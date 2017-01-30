package com.kosarict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Sadegh-Pc on 1/24/2017.
 */
@Controller
public class FollowController {

    @RequestMapping(value = "/follow", method = RequestMethod.GET)
    public ModelAndView complain() {
        ModelAndView model = new ModelAndView("/site/follow");

        return model;
    }
}
