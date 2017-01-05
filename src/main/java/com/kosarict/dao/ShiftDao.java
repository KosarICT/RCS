package com.kosarict.dao;

import com.kosarict.entity.Shift;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Younes on 06/12/2016.
 */
public interface ShiftDao {
    List<Shift> getAllShiftList();

    @Transactional(isolation = Isolation.SERIALIZABLE)
    int saveShift(Shift shiftModel);

    Shift findShiftById(short shiftId);

    @Transactional
    boolean deleteShift(short shiftId);

    List<Shift> getShifitByHospitalId(int hospitalId);
}
