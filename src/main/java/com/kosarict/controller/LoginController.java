package com.kosarict.controller;

import com.kosarict.dao.UserDao;
import com.kosarict.entity.Users;
import com.kosarict.model.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Controller
public class LoginController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(@ModelAttribute Users users,
                                  @RequestParam(value = "error", required = false) String error,
                                  @RequestParam(value = "logout", required = false) String logout) throws UnsupportedEncodingException {

        String message = "";
        if (error != null) {
            message = URLDecoder.decode(Constant.INCORRECT,"UTF-8");
        } else if (logout != null) {
            message = URLDecoder.decode(Constant.LOGOUT,"UTF-8");
        }
        return new ModelAndView("login", "message", message);
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied(Principal user) {

        ModelAndView model = new ModelAndView();

        if (user != null) {
            model.addObject("msg", "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        } else {
            model.addObject("msg",
                    "You do not have permission to access this page!");
        }

        model.setViewName("403");
        return model;

    }
}
