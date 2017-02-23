package com.kosarict.dao;

import com.kosarict.entity.RequestStatus;

/**
 * Created by Sadegh-Pc on 2/21/2017.
 */
public interface RequestStatusDao {
    RequestStatus findRequestStatusById(int requestStatusId);
}
