package com.kosarict.dao;

import com.kosarict.entity.Complain;
import com.kosarict.entity.ComplainErrand;
import com.kosarict.entity.UsersHospitalSection;

import java.util.List;

/**
 * Created by Sadegh-Pc on 12/5/2016.
 */
public interface ComplainDao {
    List<Complain> getAllComplainList();

    List<ComplainErrand> getComplainListByUserId(int userId);

    long saveComplain(Complain complainModel);

    Complain findComplainById(long complainId);

    boolean deleteComplain(long complainId);

    List<UsersHospitalSection> forwardComplain(int hospitalId, int sectionId);

    boolean trackingCodeIsExist(String trackingCode);
}
