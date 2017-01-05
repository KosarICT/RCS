package com.kosarict.dao;

import com.kosarict.entity.SendType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Younes on 09/12/2016.
 */
@Repository
public class SendTypeDaoImpl implements SendTypeDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<SendType> getAllSendTypeList() {
        String queryString = "SELECT sendType FROM SendType sendType where  sendType.enable=true ";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int saveSendType(SendType sendTypeModel) {
        return 0;
    }

    @Override
    public SendType findSendTypeById(short sendTypeId) {
        return entityManager.find(SendType.class, sendTypeId);
    }

    @Transactional
    @Override
    public boolean deleteSendType(short sendTypeId) {
        SendType sendType = findSendTypeById(sendTypeId);
        sendType.setEnable(false);
        entityManager.merge(sendType);
        return true;
    }
}
