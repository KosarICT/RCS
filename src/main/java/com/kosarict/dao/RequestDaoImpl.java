package com.kosarict.dao;

import com.kosarict.entity.Request;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Sadegh-Pc on 2/21/2017.
 */
@Repository("RequestDao")
public class RequestDaoImpl implements RequestDao {

    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<Request> getRequestList(int userId) {
        List<Request> requestList;

        String queryString = "SELECT request FROM Request request WHERE request.user.userId =:userId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("userId", userId);

        requestList = query.getResultList();

        return requestList;
    }

    @Override
    public List<Request> getRequestListByHospitalId(int hospitalId) {
        List<Request> requestList;

        String queryString = "SELECT request FROM Request request WHERE request.hospital.hospitalId =:hospitalId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("hospitalId", hospitalId);

        requestList = query.getResultList();

        return requestList;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int saveRequest(Request requestModel) {
        Request request = entityManager.merge(requestModel);
        return request.getRequestId();
    }

    @Override
    public Request findRequestById(int requestId) {
        return entityManager.find(Request.class, requestId);
    }

    @Override
    public Request findRequestByUserIdAndHospitalId(int userId, int hospitalId) {

        String queryString = "SELECT request FROM Request request " +
                "WHERE request.hospital.hospitalId =:hospitalId " +
                "AND request.user.userId=:userId " +
                "AND request.user.enable = true ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("hospitalId", hospitalId);
        query.setParameter("userId", userId);

        List<Request> requestList = query.getResultList();

        if (requestList.size() > 0) {
            return requestList.get(0);
        } else {
            return null;
        }
    }
}
