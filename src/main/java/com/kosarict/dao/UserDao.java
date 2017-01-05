package com.kosarict.dao;

import com.kosarict.entity.Users;

import java.util.List;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
public interface UserDao {
    List<Users> getAllUsersList();

    Users findUserByUserName(String userName);

    List<Users> checkUser(String userName, String password);

    int saveUser(Users userModel);

    Users findUserById(int userId);

    boolean deleteUser(int userId);

    boolean existUserName(String userName);
}
