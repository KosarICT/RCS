package com.kosarict.dao;

import com.kosarict.entity.ComplaintType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Younes on 04/12/2016.
 */
@Repository("ComplainTypeDao")
public class ComplainTypeDaoImpl implements ComplainTypeDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<ComplaintType> getAllComplaintTypeList() {
        String queryString = "SELECT complaintType FROM ComplaintType complaintType  WHERE complaintType.enable = true ";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public short saveComplaintType(ComplaintType complaintTypeModel) {
        ComplaintType complaintType = entityManager.merge(complaintTypeModel);
        return complaintType.getComplaintTypeId();
    }

    @Override
    public ComplaintType findComplaintTypeById(short ComplaintTypeId) {
        return entityManager.find(ComplaintType.class, ComplaintTypeId);
    }

    @Transactional
    @Override
    public boolean deleteComplaintType(short ComplaintTypeId) {
        ComplaintType complaintType = findComplaintTypeById(ComplaintTypeId);
        complaintType.setEnable(false);
        entityManager.merge(complaintType);
        return true;
    }
}
