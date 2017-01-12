package com.kosarict.dao;

import com.kosarict.entity.UsersHospitalSection;

import java.util.List;

/**
 * Created by Sadegh-Pc on 12/6/2016.
 */
public interface UserSectionDao {
    List<UsersHospitalSection> getUserSectionByUserId(int userId);

    UsersHospitalSection findUserHospitalSectionById(int userSectionId);

    boolean deleteUserHospitalSection(int userHospitalSectionId);

    List<UsersHospitalSection> getUserSectionBySectionId(int sectionId);

    int saveUserHospitalSection(UsersHospitalSection userHospitalSectionModel);

    boolean deleteUserSectionByUserId(int userId);

    List<UsersHospitalSection> findUserHospitalSectionByUserId(int userId);
}
