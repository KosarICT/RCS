package com.kosarict.controller;

import com.kosarict.dao.UserDao;
import com.kosarict.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
                                  @RequestParam(value = "logout", required = false) String logout) {

        String message = "";
        if (error != null) {
            message = "Incorrect username or password !";
        } else if (logout != null) {
            message = "Logout successful !";
        }
        return new ModelAndView("login", "message", message);
    }
}
