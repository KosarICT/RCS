package com.kosarict.dao;

import com.kosarict.entity.HospitalSection;
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
@Repository("HospitalSectionDao")
public class HospitalSectionDaoImpl implements HospitalSectionDao {

    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<HospitalSection> getAllHospitalSection() {
        String queryString = " SELECT hospitalSection FROM HospitalSection hospitalSection";

        Query query = entityManager.createQuery(queryString);


        return query.getResultList();
    }

    @Override
    public List<HospitalSection> getHospitalSectionsListByHospitalId(int hospitalId) {
        String queryString = "SELECT hs FROM HospitalSection hs " +
                "WHERE hs.hospital.hospitalId =:hospitalId and hs.enable=true ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("hospitalId", hospitalId);

        return query.getResultList();
    }

    @Override
    public HospitalSection findHospitalSectionById(int hospitalSectionId) {
        return entityManager.find(HospitalSection.class, hospitalSectionId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public boolean deleteHospitalSectionBySectionId(int hospitalSectionId) {
        HospitalSection hospitalSection = findHospitalSectionBySectionId(hospitalSectionId);
        entityManager.remove(hospitalSection);
        return true;
    }

    @Override
    public HospitalSection findHospitalSectionBySectionId(int hospitalSectionId) {
        return entityManager.find(HospitalSection.class, hospitalSectionId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int saveHospitalSection(HospitalSection hospitalSection) {
        HospitalSection hospitalSection1 = entityManager.merge(hospitalSection);

        return hospitalSection1.getHospitalSectionId();
    }

}
