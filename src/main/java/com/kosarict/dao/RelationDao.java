package com.kosarict.dao;

import com.kosarict.entity.Relation;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Younes on 06/12/2016.
 */
public interface RelationDao {
    List<Relation> getAllRelationList();

    @Transactional(isolation = Isolation.SERIALIZABLE)
    int saveRelation(Relation complainModel);

    Relation findRelationById(short complainId);

    @Transactional
    boolean deleteRelation(short complainId);
}
