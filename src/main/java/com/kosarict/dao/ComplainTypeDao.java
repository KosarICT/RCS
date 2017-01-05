package com.kosarict.dao;

import com.kosarict.entity.ComplaintType;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Younes on 04/12/2016.
 */
public interface ComplainTypeDao {
    List<ComplaintType> getAllComplaintTypeList();

    short saveComplaintType(ComplaintType complaintTypeModel);

    ComplaintType findComplaintTypeById(short complaintTypeId);

    boolean deleteComplaintType(short complaintTypeId);
}
