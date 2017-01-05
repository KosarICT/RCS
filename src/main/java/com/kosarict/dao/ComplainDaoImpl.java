package com.kosarict.dao;

import com.kosarict.entity.Complain;
import com.kosarict.entity.ComplainErrand;
import com.kosarict.entity.UsersHospitalSection;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Sadegh-Pc on 12/5/2016.
 */
@Repository("ComplainDao")
public class ComplainDaoImpl implements ComplainDao {

    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;


    @Override
    public List<Complain> getAllComplainList() {
        String queryString = "SELECT complain FROM Complain complain WHERE complain.enable = true ";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Override
    public List<ComplainErrand> getComplainListByUserId(int userId) {
        String queryString = "SELECT DISTINCT complainErrand FROM ComplainErrand complainErrand " +
                "JOIN complainErrand.complain complain " +
                "WHERE complain.enable = true " +
                "AND complainErrand.assignedUser.userId =:userId ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("userId", userId);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)

    @Override
    public long saveComplain(Complain complainModel) {
        Complain complain = entityManager.merge(complainModel);
        return complain.getComplainId();
    }

    @Override
    public Complain findComplainById(long complainId) {
        return entityManager.find(Complain.class, complainId);
    }

    @Override
    public boolean deleteComplain(long complainId) {
        Complain complain = findComplainById(complainId);
        complain.setEnable(false);
        saveComplain(complain);

        return true;
    }

    @Override
    public List<UsersHospitalSection> forwardComplain(int hospitalId, int sectionId) {


        String queryString = "SELECT userSection  " +
                "FROM UsersHospitalSection userSection " +
                "JOIN userSection.hospitalSection hospitalSection " +
                "WHERE hospitalSection.hospital.hospitalId =:hospitalId " +
                "AND hospitalSection.section.sectionId =:sectionId ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("hospitalId", hospitalId);
        query.setParameter("sectionId", sectionId);


        return query.getResultList();
    }

    @Override
    public boolean trackingCodeIsExist(String trackingCode) {

        boolean isExist = false;
        List<Complain> complainList;

        String queryString = "SELECT complain FROM Complain complain where  complain.enable=true  and complain.trackingCode=:trackingCode";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("trackingCode", trackingCode);

        complainList = query.getResultList();

        if (complainList.size() > 0) {
            isExist = true;
        }

        return isExist;
    }
}
