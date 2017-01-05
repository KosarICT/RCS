package com.kosarict.dao;

import com.kosarict.entity.Hospital;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Younes on 03/12/2016.
 */
public interface HospitalDao {
    List<Hospital> getAllHospitalList();

    int saveHospital(Hospital hospitalModel);

    Hospital findHospitalById(int hospitalId);

    boolean deleteHospital(int hospitalId);
}
