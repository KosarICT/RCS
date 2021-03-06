package com.kosarict.dao;

import com.kosarict.entity.Ticket;
import com.kosarict.entity.Users;
import com.kosarict.model.Constant;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.validation.Constraint;
import java.util.List;

/**
 * Created by Sadegh-Pc on 11/28/2016.
 */
@Repository("UserDao")
public class UserDaoImpl implements UserDao {

    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;


    @Override
    public List<Users> getAllUsersList() {
        String queryString = "SELECT user FROM Users user  WHERE user.enable = true ";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public List<Users> getUserListBuHospitalId(int hospitalId){
        Session session = entityManager.unwrap(Session.class);

        String queryString = "SELECT\n" +
                "\tUsers.*\n" +
                "FROM\n" +
                "\tHospitalSection\n" +
                "JOIN UsersHospitalSection ON HospitalSection.HospitalSection_Id = UsersHospitalSection.HospitalSection_Id\n" +
                "JOIN Users ON UsersHospitalSection.User_Id = Users.User_Id\n" +
                "WHERE Users.Enable = 1 AND HospitalSection.Hospital_Id = " + hospitalId;

        List query = session.createSQLQuery(queryString).addEntity(Users.class).list();


        return query;
    }

    @Override
    public List<Users> getAllUsersList(int hospitalId) {
        return null;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public List<Users> getAllUsersList1() {
        String queryString = "SELECT user FROM Users user  WHERE user.enable = true ";
        Session session = entityManager.unwrap(Session.class);

        List query =
                session.createSQLQuery("SELECT * FROM Users").addEntity(Users.class).list();

        return query;
    }

    @Override
    public Users findUserByUserName(String userName) {
        try {
            Users user;

            String queryString = "SELECT user FROM Users user  WHERE user.userName=:userName AND user.enable = true ";

            Query query = entityManager.createQuery(queryString);
            query.setParameter("userName", userName);

            user = (Users) query.getResultList().get(0);

            return user;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Users> checkUser(String userName, String password) {

        String queryString = "SELECT user FROM Users user  WHERE user.userName=:userName AND user.password=:password AND user.enable = true ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("userName", userName);
        query.setParameter("password", password);

        return query.getResultList();
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int saveUser(Users userModel) {

        Users user = entityManager.merge(userModel);

        return user.getUserId();


    }

    @Override
    public Users findUserByPersonalNumber(String personalNumber) {
        try {
            Users user;

            String queryString = "SELECT user FROM Users user  WHERE user.personalNumber=:personalNumber AND user.enable = true ";

            Query query = entityManager.createQuery(queryString);
            query.setParameter("personalNumber", personalNumber);

            user = (Users) query.getResultList().get(0);

            return user;
        } catch (Exception ex) {
            return null;
        }
    }


    @Override
    public Users findUserByImei(String imei) {
        try {
            Users user;

            String queryString = "SELECT user FROM Users user  WHERE user.imei=:imei AND user.enable = true ";

            Query query = entityManager.createQuery(queryString);
            query.setParameter("imei", imei);

            user = (Users) query.getResultList().get(0);

            return user;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Users findUserByNationalCode(String nationalCode) {
        try {
            Users user;

            String queryString = "SELECT user FROM Users user  WHERE user.nationalCode=:nationalCode AND user.enable = true ";

            Query query = entityManager.createQuery(queryString);
            query.setParameter("nationalCode", nationalCode);

            user = (Users) query.getResultList().get(0);

            return user;
        } catch (Exception ex) {
            return null;
        }
    }


    @Override
    public Users findUserById(int userId) {
        return entityManager.find(Users.class, userId);
    }

    @Transactional
    @Override
    public boolean deleteUser(int userId) {
        Users user = findUserById(userId);
        user.setEnable(false);
        entityManager.merge(user);
        return true;
    }


    @Override
    public boolean existUserName(String userName) {
        String queryString = "SELECT user FROM Users user  WHERE user.userName=:userName ";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("userName", userName);
        return query.getResultList().size() > 0;
    }

    @Override
    public boolean isManagerOrMiddleManager(int userId) {
        String queryString = "SELECT userRple FROM UserRole userRple  WHERE userRple.users.userId=:userId and (userRple.role.roleId=:managerRole or userRple.role.roleId=:middelManagerRole)";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("userId", userId);
        query.setParameter("middelManagerRole", Constant.MiddelManagerRole);
        query.setParameter("managerRole", Constant.ManagerRole);
        return query.getResultList().size() > 0;
    }
}
