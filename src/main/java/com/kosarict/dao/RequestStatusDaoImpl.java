package com.kosarict.dao;

import com.kosarict.entity.RequestStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Created by Sadegh-Pc on 2/21/2017.
 */
@Repository("RequestStatusDao")
public class RequestStatusDaoImpl implements RequestStatusDao {

    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public RequestStatus findRequestStatusById(int requestStatusId) {
        return entityManager.find(RequestStatus.class, requestStatusId);
    }
}
