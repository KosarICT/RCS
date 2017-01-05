package com.kosarict.dao;

import com.kosarict.entity.Hospital;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Younes on 03/12/2016.
 */
@Repository
public class HospitalDaoImpl implements HospitalDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<Hospital> getAllHospitalList() {
        String queryString = "SELECT hospital FROM Hospital hospital  WHERE hospital.enable = true ";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int saveHospital(Hospital hospitalModel) {
        Hospital hospital = entityManager.merge(hospitalModel);
        return hospital.getHospitalId();
    }

    @Override
    public Hospital findHospitalById(int hospitalId) {
        return entityManager.find(Hospital.class, hospitalId);
    }

    @Transactional
    @Override
    public boolean deleteHospital(int hospitalId) {
        Hospital hospital = findHospitalById(hospitalId);
        hospital.setEnable(false);
        entityManager.merge(hospital);
        return true;
    }
}
