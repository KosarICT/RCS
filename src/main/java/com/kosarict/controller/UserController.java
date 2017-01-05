package com.kosarict.controller;

import com.kosarict.dao.HospitalDao;
import com.kosarict.dao.HospitalSectionDao;
import com.kosarict.dao.UserDao;
import com.kosarict.dao.UserSectionDao;
import com.kosarict.entity.Hospital;
import com.kosarict.entity.HospitalSection;
import com.kosarict.entity.Users;
import com.kosarict.entity.UsersHospitalSection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private HospitalDao hospitalDao;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private HospitalSectionDao hospitalSectionDao;

    @Autowired
    private UserSectionDao userSectionDao;


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView user() {
        ModelAndView model = new ModelAndView("user");
        model.addObject("lists", getUsersList());
        model.addObject("hospitalList", getHospitalLists());

        return model;
    }

    @RequestMapping(value = "/user/api/deleteUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteUser(@RequestBody String userId) {
        try {
            int id = Integer.parseInt(userId);
            userDao.deleteUser(id);
            return String.valueOf(true);
        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }


    @RequestMapping(value = "/user/api/addUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String addUser(@RequestBody String model) {

        try {
            JSONArray jsonArray = new JSONArray(model);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            int userId = jsonObject.getInt("userId");
            String userName = jsonObject.getString("userName");
            String password = jsonObject.getString("password");
            int personalNumber = jsonObject.getInt("personalNumber");
            String firstName = jsonObject.getString("firstName");
            String lastName = jsonObject.getString("lastName");
            String displayName = jsonObject.getString("displayName");
            String tel = jsonObject.getString("tel");
            String mobile = jsonObject.getString("mobile");
            String imageName = jsonObject.getString("imageName");
            short locked = Short.parseShort(String.valueOf(jsonObject.getInt("locked")));
            int hospitalSectionId = jsonObject.getInt("hospitalSectionId");


            Users user;

            if (userId <= 0) {
                user = new Users();
                user.setPassword(password);
            } else {
                user = userDao.findUserById(userId);
            }


            user.setUserId(userId);
            user.setUserName(userName);
            user.setPersonalNumber(String.valueOf(personalNumber));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setDisplayName(displayName);
            user.setImageName(imageName);
            user.setTel(tel);
            user.setMobile(mobile);
            user.setLocked(locked);
            user.setEnable(true);

            int newUserId = userDao.saveUser(user);

            userSectionDao.deleteUserSectionByUserId(newUserId);

            UsersHospitalSection userHospitalSection = new UsersHospitalSection();

            userHospitalSection.setUsersHospitalSectionId(0);
            userHospitalSection.setUser(userDao.findUserById(newUserId));
            HospitalSection hospitalSection = hospitalSectionDao.findHospitalSectionById(hospitalSectionId);
            userHospitalSection.setHospitalSection(hospitalSection);

            userSectionDao.saveUserHospitalSection(userHospitalSection);


            return String.valueOf(true);

        } catch (Exception ex) {
            return String.valueOf(false);
        }


    }

    @RequestMapping(value = "/user/api/getUserForEdit", method = RequestMethod.POST)
    public
    @ResponseBody
    String getUserForEdit(@RequestBody String userId) {

        try {
            JSONArray jsonArray = new JSONArray();
            int id = Integer.parseInt(userId);

            Users user = userDao.findUserById(id);
            List<UsersHospitalSection> usersHospitalSection = userSectionDao.findUserHospitalSectionByUserId(id);

            if (user != null) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("userId", user.getUserId());
                jsonObject.put("userName", user.getUserName());
                jsonObject.put("firstName", user.getFirstName());
                jsonObject.put("lastName", user.getLastName());
                jsonObject.put("displayName", user.getDisplayName());
                jsonObject.put("personalNumber", user.getPersonalNumber());
                jsonObject.put("imageName", user.getImageName());
                jsonObject.put("tel", user.getTel());
                jsonObject.put("mobile", user.getMobile());
                jsonObject.put("locked", user.getLocked());
                jsonObject.put("hospitalSection", usersHospitalSection);

                jsonArray.put(jsonObject);
            }

            return jsonArray.toString();
        } catch (Exception ex) {
            return String.valueOf(false);
        }


    }

    @RequestMapping(value = "/user/api/checkUserName", method = RequestMethod.POST)
    public
    @ResponseBody
    String checkUserName(@RequestBody String userName) {
        try {
            if (userDao.existUserName(userName))
                return String.valueOf(true);
            else
                return String.valueOf(false);


        } catch (Exception ex) {
            return String.valueOf(-1);
        }
    }

    @RequestMapping(value = "/user/api/getSectionListByHospitalId", method = RequestMethod.POST)
    public
    @ResponseBody
    String getSectionListByHospitalId(@RequestBody String hospitalId) {
        try {
            int id = Integer.parseInt(hospitalId);
            JSONArray jsonArray = new JSONArray();

            List<HospitalSection> hospitalSectionList = hospitalSectionDao.getHospitalSectionsListByHospitalId(id);

            for (HospitalSection hospitalSection : hospitalSectionList) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("sectionId", hospitalSection.getHospitalSectionId());
                jsonObject.put("title", hospitalSection.getSection().getTitle());

                jsonArray.put(jsonObject);
            }

            return jsonArray.toString();

        } catch (Exception ex) {
            return String.valueOf(-1);
        }
    }


    private List<Users> getUsersList() {
        return userDao.getAllUsersList();
    }

    private List<Hospital> getHospitalLists() {
        return hospitalDao.getAllHospitalList();
    }

    @RequestMapping(value = "/user/api/uploadImage", method = RequestMethod.POST)
    public
    @ResponseBody
    String doUpload(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();

            String uniqFileName = fileName;

            File rootDir = new File(servletContext.getRealPath("/static/userImage"));

            if (!rootDir.exists())
                rootDir.mkdirs();


            if (!"".equalsIgnoreCase(fileName)) {
                file.transferTo(new File(rootDir.getAbsolutePath() + File.separator + uniqFileName));
            }

            return String.valueOf(true);
        } catch (IOException e) {
            return String.valueOf(false);
        }
    }

    /*@RequestMapping(value = "/user/api/getSectionOfHospital", method = RequestMethod.POST)
    public
    @ResponseBody
    String getSectionOfHospital(@RequestBody String hospitalId) {
        int id = Integer.parseInt(hospitalId);

        List<HospitalSection> hospitalSectionList = hospitalSectionDao.getHospitalSectionsListByHospitalId(id);

        JSONArray jsonArray = new JSONArray();

        for (HospitalSection hospitalSection : hospitalSectionList) {
            JSONObject jsonObject = new JSONObject();


            jsonObject.put("sectionId", hospitalSection.getSection().getSectionId());
            jsonObject.put("title", hospitalSection.getSection().getTitle());

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }*/
}
