package com.kosarict.dao;

import com.kosarict.entity.Complainant;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Younes on 07/12/2016.
 */
public interface ComplainantDao {
    List<Complainant> getAllComplainantList();

    @Transactional(isolation = Isolation.SERIALIZABLE)
    int saveComplainant(Complainant complainantModel);

    Complainant findComplainantById(short complainantId);

    @Transactional
    boolean deleteComplainant(short complainantId);

}
