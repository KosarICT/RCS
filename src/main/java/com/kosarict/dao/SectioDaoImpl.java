package com.kosarict.dao;

import com.kosarict.entity.Section;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Younes on 06/12/2016.
 */
@Repository
public class SectioDaoImpl implements SectionDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<Section> getAllSectionList() {
        String queryString = "SELECT section FROM Section section  WHERE section.enable = true ";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Override
    public List<Section> getSections(int hospitalId){
        String queryString = "SELECT hospitalSection.section FROM HospitalSection hospitalSection JOIN hospitalSection.section" +
                "  WHERE hospitalSection.section.enable = true and hospitalSection.hospital.hospitalId=:hospitalId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("hospitalId", hospitalId);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int saveSection(Section sectionModel) {
        return 0;
    }

    @Override
    public Section findSectionById(int sectionId) {
        return entityManager.find(Section.class, sectionId);
    }

    @Transactional
    @Override
    public boolean deleteSection(int sectionId) {
        Section section = findSectionById(sectionId);
        section.setEnable(false);
        entityManager.merge(section);
        return true;
    }
}
