package com.kosarict.controller;

import com.kosarict.dao.RequestDao;
import com.kosarict.dao.RequestStatusDao;
import com.kosarict.dao.UserDao;
import com.kosarict.dao.UserSectionDao;
import com.kosarict.entity.Request;
import com.kosarict.entity.RequestStatus;
import com.kosarict.entity.Users;
import com.kosarict.entity.UsersHospitalSection;
import com.kosarict.model.Constant;
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
 * Created by Sadegh-Pc on 2/22/2017.
 */
@Controller
public class ADRequestList {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserSectionDao userSectionDao;

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private RequestStatusDao requestStatusDao;




    @RequestMapping(value = "/adRequestList", method = RequestMethod.GET)
    public ModelAndView getView() {
        ModelAndView model = new ModelAndView("frmRequest");

        return model;
    }

    @RequestMapping(value = "/request/api/getData", method = RequestMethod.GET)
    public
    @ResponseBody
    String getData() {
        try {
            int hospitalId = getCurrentHospital();
            List<Request> requestList = requestDao.getRequestListByHospitalId(hospitalId);

            JSONArray jsonArray = new JSONArray();

            for (Request request : requestList) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("requestId", request.getRequestId());
                jsonObject.put("hospitalTitle", request.getHospital().getName());
                jsonObject.put("requestStatusId", request.getRequestStatus().getRequestStatusId());
                jsonObject.put("requestStatusTitle", request.getRequestStatus().getTitle());
                jsonObject.put("user", request.getUser().getFirstName() + " " + request.getUser().getLastName());

                jsonArray.put(jsonObject);
            }


            return jsonArray.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(value = "/request/api/changeUserStatus", method = RequestMethod.POST)
    public
    @ResponseBody
    String changeUserStatus(@RequestBody String model) {
        try {
            JSONArray jsonArray = new JSONArray(model);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            int requestId = jsonObject.getInt("requestId");
            boolean status = jsonObject.getBoolean("status");

            Request request = requestDao.findRequestById(requestId);

            RequestStatus requestStatus;

            if(status){
                requestStatus = requestStatusDao.findRequestStatusById(Constant.EnableStatus);
            }else {
                requestStatus = requestStatusDao.findRequestStatusById(Constant.DisableStatus);
            }

            request.setRequestStatus(requestStatus);

            int newRequestId = requestDao.saveRequest(request);

            return String.valueOf(true);
        } catch (Exception ex) {
            return String.valueOf(false);
        }
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

}
