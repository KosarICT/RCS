package com.kosarict.dao;

import com.kosarict.entity.Request;

import java.util.List;

/**
 * Created by Sadegh-Pc on 2/21/2017.
 */
public interface RequestDao {
    List<Request> getRequestList(int userId);

    List<Request> getRequestListByHospitalId(int hospitalId);

    int saveRequest(Request requestModel);

    Request findRequestById(int requestId);

    Request findRequestByUserIdAndHospitalId(int userId, int hospitalId);
}
