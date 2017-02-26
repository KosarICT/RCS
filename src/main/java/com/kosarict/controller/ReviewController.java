package com.kosarict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Ali-Pc on 2/24/2017.
 */
@Controller
public class ReviewController {



    @RequestMapping(value = "/{hospitalId}/review", method = RequestMethod.GET)
    public ModelAndView complain(@PathVariable(value = "hospitalId") String hospitalId,
                                 @ModelAttribute("pojo") String pojo) {


        return new ModelAndView("/site/review");
    }

}
