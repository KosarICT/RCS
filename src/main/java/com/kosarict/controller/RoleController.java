package com.kosarict.controller;

import com.kosarict.dao.RoleDao;
import com.kosarict.dao.UserDao;
import com.kosarict.dao.UserRoleDao;
import com.kosarict.entity.Role;
import com.kosarict.entity.UserRole;
import com.kosarict.entity.Users;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * Created by Ali-Pc on 1/7/2017.
 */
public class RoleController {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private UserRoleDao userRoleDao;

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public ModelAndView getViewRole() {
        ModelAndView model = new ModelAndView("role");
        model.addObject("lists", getRols());
        model.addObject("userList",getUsersList());
        return model;
    }

    private List<Users> getUsersList() {
        return userDao.getAllUsersList();
    }

    private List<Role> getRols(){
        return roleDao.getRoles();
    }

    @RequestMapping(value = "/role/api/addRole", method = RequestMethod.POST)
    public
    @ResponseBody
    String addRole(@RequestBody String model) {

        try {
            JSONArray jsonArray = new JSONArray(model);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            Integer roleId = jsonObject.getInt("roleId");
            String roleName = jsonObject.getString("roleName");
            String description = jsonObject.getString("description");

            Role role;

            if (roleId <= 0) {
                role = new Role();
            } else {
                role = roleDao.getRole(roleId.shortValue());
            }


            role.setName(roleName);
            role.setDescription(description);

            short newRoleId = roleDao.saveRole(role);


            List<UserRole> userRoles=userRoleDao.getUserRole(newRoleId);
            JSONArray users=jsonObject.getJSONArray("users");

            for (UserRole userRole :userRoles){
                int length=users.length();
                int i=0;
                for (;i<length;i++){
                    JSONObject userRoleJSONObject = users.getJSONObject(i);

                    int userId = userRoleJSONObject.getInt("userId");

                    if (userId == userRole.getUsers().getUserId()) {
                        users.remove(i);
                        break;
                    }
                }

                if(i==length){
                    userRoleDao.deleteUserRole(userRole.getUserRoleId());
                }
            }

            int j=0;
            Role role1= roleDao.getRole(newRoleId);
            for (;j<users.length();j++){
                UserRole userRole=new UserRole();
                JSONObject userRoleJSONObject = users.getJSONObject(j);

                Integer userId = userRoleJSONObject.getInt("userId");
                Users users1 =userDao.findUserById(userId);
                userRole.setUsers(users1);
                userRole.setRole(role1);

                userRoleDao.save(userRole);
            }


            return String.valueOf(true);

        } catch (Exception ex) {
            return String.valueOf(false);
        }


    }


}
