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

    @Autowired
    private NotificationAnswerDao notificationAnswerDao;

    @Autowired
    private NotificationUserSeenDao notificationUserSeenDao;

    @RequestMapping(value = "/notification", method = RequestMethod.GET)
    public ModelAndView notification() {
        ModelAndView model = new ModelAndView("notification");
        //model.addObject("notificationLists", getNotification());
        model.addObject("hospitalSectionList", getSectionList());
        model.addObject("isManagerOrMiddleManager", isManagerOrMiddleManager());
        return model;

    }

    private boolean isManagerOrMiddleManager(){
        Users users = getCurrentUser();

        return userDao.isManagerOrMiddleManager(users.getUserId());
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

    @RequestMapping(value = "/notificationController/api/getData", method = RequestMethod.GET)
    public
    @ResponseBody
    String getData(){
        Users users=getCurrentUser();

        JSONArray dataArray = new JSONArray();

        List<Notification> notificationList=getNotification();
        for (Notification notification: notificationList){
            JSONObject dataItem = new JSONObject();
            dataItem.put("notificationId",notification.getNotificationId());
            dataItem.put("datetime",notification.getDatetime());
            dataItem.put("subject",notification.getSubject());
            dataItem.put("isNew",notificationUserSeenDao.isNewit(notification.getNotificationId(),users.getUserId()));
            dataArray.put(dataItem);
        }
        return dataArray.toString();
    }

    @RequestMapping(value = "/notificationController/api/save", method = RequestMethod.POST)
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

                NotificationUserSeen notificationUserSeen=new NotificationUserSeen();
                notificationUserSeen.setNotification(notification1);
                notificationUserSeen.setUser(users1);

                notificationUserSeenDao.save(notificationUserSeen);


            }
            return String.valueOf(true);
        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }

    @RequestMapping(value = "/notificationController/api/getNotification", method = RequestMethod.POST)
    public
    @ResponseBody
    String getNotification(@RequestBody String notificationId){
        try {
            JSONArray jsonArray = new JSONArray();
            long id = Long.parseLong(notificationId);
            Users currentUser = getCurrentUser();
            List<NotificationAnswer> notificationAnswers;
            List<NotificationAssign> notificationAssigns;

            Notification notification=notificationDao.findNotification(id);

            if(notification != null){

                JSONObject jsonObject = new JSONObject();
                JSONArray userList = new JSONArray();

                notificationAssigns = notificationAssignDao.getNotificationAssinByNotification(id);
                if(currentUser.getUserId()==notification.getSubmitUser().getUserId()) {
                    notificationAnswers = notificationAnswerDao.getNotificationAnswerByNotification(id);
                }else {
                    notificationAnswers=notificationAnswerDao.getNotificationAnswerByNotificationUser(id,currentUser.getUserId());
                }

                jsonObject.put("subjct",notification.getSubject());
                jsonObject.put("body",notification.getBody());
                jsonObject.put("dateTime",notification.getDatetime());
                jsonObject.put("notificationId",notification.getNotificationId());
                jsonObject.put("notificationAnswers",notificationAnswers);

                for (NotificationAssign user : notificationAssigns) {
                    JSONObject jsonObject1 = new JSONObject();

                    jsonObject1.put("userId", user.getUser().getUserId());
                    jsonObject1.put("name", user.getUser().getFirstName() + " " + user.getUser().getLastName());

                    userList.put(jsonObject1);
                }

                jsonObject.put("notificationAssigns",userList);

                if(currentUser.getUserId()!=notification.getSubmitUser().getUserId()) {
                    userList=new JSONArray();
                    JSONObject jsonObject1 = new JSONObject();

                    jsonObject1.put("userId", notification.getSubmitUser().getUserId());
                    jsonObject1.put("name", notification.getSubmitUser().getFirstName() + " " + notification.getSubmitUser().getLastName());

                    userList.put(jsonObject1);
                }

                notificationUserSeenDao.delete(notification.getNotificationId(),currentUser.getUserId());

                jsonObject.put("userCanGetAnswer",userList);
                jsonArray.put(jsonObject);
            }

            return jsonArray.toString();
        }catch (Exception ex){
            return String.valueOf(false);
        }

    }



    @RequestMapping(value = "/notificationController/api/saveAnswer", method = RequestMethod.POST)
    public
    @ResponseBody
    String SaveAnswer(@RequestBody String model){
        try {
            JSONArray jsonArray = new JSONArray(model);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            String body=jsonObject.getString("body");
            long notificationId=jsonObject.getLong("notificationId");


            Notification notification=notificationDao.findNotification(notificationId);
            JSONArray userIds=jsonObject.getJSONArray("userIds");
            Users currentUser=getCurrentUser();
            PersianCalendar persianCalendar = new PersianCalendar();
            String currentDate = persianCalendar.getIranianSimpleDate();

            int i=0;
            for (;i<userIds.length();i++){
                int userId= userIds.getInt(i);
                Users users=userDao.findUserById(userId);
                NotificationAnswer notificationAnswer=new NotificationAnswer();
                notificationAnswer.setDatetime(currentDate);
                notificationAnswer.setNotification(notification);
                notificationAnswer.setSubmitUser(currentUser);
                notificationAnswer.setAssignUser(users);
                notificationAnswer.setBody(body);

                notificationAnswerDao.save(notificationAnswer);

            }
            List<NotificationAnswer> notificationAnswers;
            if(currentUser.getUserId()==notification.getSubmitUser().getUserId()) {
                notificationAnswers = notificationAnswerDao.getNotificationAnswerByNotification(notificationId);
            }else {
                notificationAnswers=notificationAnswerDao.getNotificationAnswerByNotificationUser(notificationId,currentUser.getUserId());
            }
            JSONObject jsonObject1 = new JSONObject();
            JSONArray jsonArray1 = new JSONArray();
            jsonObject1.put("notificationAnswers",notificationAnswers);
            jsonArray1.put(jsonObject1);
            return jsonArray1.toString();
        }catch (Exception ex){
            return String.valueOf(false);
        }
    }



    }



