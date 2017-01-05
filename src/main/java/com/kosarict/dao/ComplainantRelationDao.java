package com.kosarict.dao;

import com.kosarict.entity.ComplainantRelation;


import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Younes on 09/12/2016.
 */
public interface ComplainantRelationDao {

    List<ComplainantRelation> getAllComplainantRelationList();

    long saveComplainantRelation(ComplainantRelation complainantRelationModel);

    ComplainantRelation findComplainantRelationById(short complainantRelationId);

    @Transactional
    boolean deleteComplainantRelation(short complainantRelationId);
}
