package com.kosarict.dao;

import com.kosarict.entity.Shift;
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
public class ShiftDaoImpl implements ShiftDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<Shift> getAllShiftList() {
        String queryString = "SELECT shift FROM Shift shift  WHERE shift.eanble = true ";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int saveShift(Shift shiftModel) {
        return 0;
    }

    @Override
    public Shift findShiftById(short shiftId) {
        return entityManager.find(Shift.class, shiftId);
    }

    @Transactional
    @Override
    public boolean deleteShift(short shiftId) {
        Shift shift = findShiftById(shiftId);
        shift.setEanble(false);
        entityManager.merge(shift);
        return true;
    }

    @Override
    public List<Shift> getShifitByHospitalId(int hospitalId){
        String queryString = "SELECT hospitalShifit.shift FROM HospitalShift hospitalShifit JOIN hospitalShifit.shift" +
                "  WHERE hospitalShifit.shift.enable = true and hospitalShifit.hospital.hospitalId=:hospitalId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("hospitalId", hospitalId);

        return query.getResultList();
    }

}

