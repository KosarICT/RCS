package com.kosarict.controller;

import com.kosarict.dao.*;
import com.kosarict.entity.*;
import com.kosarict.tools.PersianCalendar;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Ali-Pc on 2/3/2017.
 */
@Controller
public class NotificationController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private HospitalSectionDao hospitalSectionDao;

    @Autowired
    private UserSectionDao userSectionDao;

    @Autowired
    private NotificationAssignDao notificationAssignDao;

    @RequestMapping(value = "/notification", method = RequestMethod.GET)
    public ModelAndView notification() {
        ModelAndView model = new ModelAndView("notification");
        model.addObject("notificationLists", getNotification());
        model.addObject("hospitalSectionList", getSectionList());
        return model;

    }


    private List<HospitalSection> getSectionList() {
        int hospitalId=getCurrentHospital();
        return hospitalSectionDao.getHospitalSectionsListByHospitalId(hospitalId);
    }

    private Users getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = userDetails.getUsername();
        Users user = userDao.findUserByUserName(userName);

        return user;
    }

    private int getCurrentHospital() {
        Users user = getCurrentUser();
        List<UsersHospitalSection> usersHospitalSectionList = userSectionDao.findUserHospitalSectionByUserId(user.getUserId());

        int hospitalId = 0;

        for (UsersHospitalSection usersHospitalSection : usersHospitalSectionList) {
            hospitalId = usersHospitalSection.getHospitalSection().getHospital().getHospitalId();
        }

        return hospitalId;
    }
    private List<Notification> getNotification() {
        Users users = getCurrentUser();

        List<Notification> notifications = notificationDao.getNotificationByUserId(users.getUserId());

        return notifications;
    }

    @RequestMapping(value = "/NotificationController/api/getData", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Notification> getData(){
        JSONArray dataArray = new JSONArray();
        JSONObject dataItem = new JSONObject();
        dataItem.put("list",getNotification());
        dataArray.put(dataItem);
        return getNotification();
    }

    @RequestMapping(value = "/NotificationController/api/save", method = RequestMethod.POST)
    public
    @ResponseBody
    String save(@RequestBody String model) {
        try {
            JSONArray jsonArray = new JSONArray(model);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            String subject=jsonObject.getString("subject");
            String body=jsonObject.getString("body");
            JSONArray userIds=jsonObject.getJSONArray("userIds");


            Users users=getCurrentUser();

            PersianCalendar persianCalendar = new PersianCalendar();
            String currentDate = persianCalendar.getIranianSimpleDate();

            Notification notification=new Notification();
            notification.setBody(body);
            notification.setSubject(subject);
            notification.setSubmitUser(users);
            notification.setDatetime(currentDate);

            long notificationId = notificationDao.Save(notification);

            Notification notification1=notificationDao.findNotification(notificationId);

            int i=0;
            for (;i<userIds.length();i++){
                int userId = userIds.getInt(i);
                Users users1=userDao.findUserById(userId);
                NotificationAssign notificationAssign=new NotificationAssign();
                notificationAssign.setNotification(notification1);
                notificationAssign.setUser(users1);

                notificationAssignDao.save(notificationAssign);
            }
            return String.valueOf(true);
        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }

    }



