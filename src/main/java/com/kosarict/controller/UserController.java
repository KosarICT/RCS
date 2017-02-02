package com.kosarict.controller;

import com.kosarict.dao.*;
import com.kosarict.entity.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private UserRoleDao userRoleDao;


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView user() {
        ModelAndView model = new ModelAndView("user");
        model.addObject("lists", getUsersList());
        model.addObject("hospitalList", getHospitalLists());

        if (checkSuperUser()) {
            model.addObject("isSuperUser", 1);
        } else {
            model.addObject("isSuperUser", 0);
            model.addObject("hospitalId", getCurrentHospital());
        }
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
            user.setSuperUser(false);

            int newUserId = userDao.saveUser(user);


            List<UsersHospitalSection> usersHospitalSectionList = userSectionDao.findUserHospitalSectionByUserId(newUserId);
            JSONArray hospitalSection = jsonObject.getJSONArray("hospitalSections");

            for (UsersHospitalSection usersHospitalSection : usersHospitalSectionList) {
                int length = hospitalSection.length();
                int i = 0;
                for (; i < length; i++) {
                    JSONObject userHospitalSectionJSONObject = hospitalSection.getJSONObject(i);

                    int hospitalSectionId = userHospitalSectionJSONObject.getInt("hospitalSectionId");

                    if (hospitalSectionId == usersHospitalSection.getHospitalSection().getHospitalSectionId()) {
                        hospitalSection.remove(i);
                        break;
                    }
                }

                if (i == length) {
                    userSectionDao.deleteUserHospitalSection(usersHospitalSection.getUsersHospitalSectionId());
                }
            }

            int j = 0;
            Users users = userDao.findUserById(newUserId);
            for (; j < hospitalSection.length(); j++) {
                UsersHospitalSection usersHospitalSection = new UsersHospitalSection();
                JSONObject userRoleJSONObject = hospitalSection.getJSONObject(j);

                Integer hospitalSectionId = userRoleJSONObject.getInt("hospitalSectionId");
                HospitalSection hospitalSection1 = hospitalSectionDao.findHospitalSectionById(hospitalSectionId);
                usersHospitalSection.setUser(users);
                usersHospitalSection.setHospitalSection(hospitalSection1);

                userSectionDao.saveUserHospitalSection(usersHospitalSection);
            }


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


            //List<UserRole> rolesUser=userRoleDao.getUserRole(id);
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
                jsonObject.put("usershospitalSection", usersHospitalSection);
                if (usersHospitalSection.size() > 0) {
                    List<HospitalSection> hospitalSections = hospitalSectionDao.getHospitalSectionsListByHospitalId(usersHospitalSection.get(0).getHospitalSection().getHospital().getHospitalId());
                    jsonObject.put("hospitalSection", hospitalSections);
                }
                //jsonObject.put("roles",rolesUser);

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
        try {
            return userDao.getAllUsersList();
        } catch (Exception ex) {
            int x = 0;
            return null;
        }

    }

    private List<Hospital> getHospitalLists() {
        return hospitalDao.getAllHospitalList();
    }

    private List<Role> getRols() {
        return roleDao.getRoles();
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

    private Users getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = userDetails.getUsername();
        Users user = userDao.findUserByUserName(userName);

        return user;
    }

    private boolean checkSuperUser() {
        Users user = getCurrentUser();

        return user.isSuperUser();
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

    @RequestMapping(value = "/user/api/getSectionList", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Section> getSectionList() {
        return sectionDao.getAllSectionList();
    }

}
