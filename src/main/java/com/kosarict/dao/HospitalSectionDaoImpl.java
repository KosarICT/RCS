package com.kosarict.dao;

import com.kosarict.entity.HospitalSection;
import com.kosarict.entity.Section;
import com.kosarict.entity.Ticket;
import org.hibernate.Session;
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
                "WHERE hs.hospital.hospitalId =:hospitalId and hs.enable=true and hs.section.view=true " +
                "and hs.section.enable=true";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("hospitalId", hospitalId);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public List<Section> getSectionList(int hospitalId) {
        Session session = entityManager.unwrap(Session.class);

        String queryString = "Select Section.* From Section " +
                "Join HospitalSection ON Section.Section_Id = HospitalSection.Section_Id " +
                "WHERE HospitalSection.Hospital_Id = " + hospitalId + " AND Section.Enable = 1 AND Section.IsView = 1";

        List query = session.createSQLQuery(queryString).addEntity(Section.class).list();


        return query;
    }


    @Override
    public HospitalSection findHospitalSectionById(int hospitalSectionId) {
        return entityManager.find(HospitalSection.class, hospitalSectionId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public boolean deleteHospitalSectionBySectionId(int hospitalSectionId) {
        HospitalSection hospitalSection = findHospitalSectionBySectionId(hospitalSectionId);
        hospitalSection.setEnable(false);
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
