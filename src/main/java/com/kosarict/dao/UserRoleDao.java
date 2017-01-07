package com.kosarict.dao;

import com.kosarict.entity.UserRole;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Ali-Pc on 1/6/2017.
 */
public interface UserRoleDao {

    List<UserRole> getUserRole(int userId);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    int save(UserRole userRole);

    @Transactional
    boolean deleteUserRole(int userRoleId);


}
