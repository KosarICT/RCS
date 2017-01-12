package com.kosarict.dao;

import com.kosarict.entity.UsersHospitalSection;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Sadegh-Pc on 12/6/2016.
 */
@Repository("UserSectionDao")
public class UserSectionDaoImpl implements UserSectionDao {

    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;


    @Override
    public List<UsersHospitalSection> getUserSectionByUserId(int userId) {
        return null;
    }

    @Override
    public UsersHospitalSection findUserHospitalSectionById(int userHospitalSectionId) {
        UsersHospitalSection usersHospitalSection=entityManager.find(UsersHospitalSection.class,userHospitalSectionId);
        return usersHospitalSection;

    }

    @Transactional
    @Override
    public boolean deleteUserHospitalSection(int userHospitalSectionId) {
        UsersHospitalSection usersHospitalSection=findUserHospitalSectionById(userHospitalSectionId);
        entityManager.remove(usersHospitalSection);
        return true;
    }

    @Override
    public List<UsersHospitalSection> getUserSectionBySectionId(int sectionId) {
        String queryString = "SELECT us FROM UsersHospitalSection us WHERE us.hospitalSection.section.sectionId =" + sectionId;

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int saveUserHospitalSection(UsersHospitalSection userHospitalSectionModel) {

        UsersHospitalSection userHospitalSection = entityManager.merge(userHospitalSectionModel);

        return userHospitalSection.getUsersHospitalSectionId();


    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public boolean deleteUserSectionByUserId(int userId) {
        List<UsersHospitalSection> userHospitalSections = findUserHospitalSectionByUserId(userId);
        for(UsersHospitalSection i:userHospitalSections){
            entityManager.remove(i);
        }
        return true;
    }


    @Override
    public List<UsersHospitalSection> findUserHospitalSectionByUserId(int userId) {
        String queryString = "SELECT us FROM UsersHospitalSection us WHERE us.user.userId=" + userId;
        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }
}
