package com.kosarict.dao;

import com.kosarict.entity.Appreciation;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Younes on 16/12/2016.
 */
public interface ADAppreciationDao {

    List<Appreciation> getAllAppreciationList();

    long saveAppreciation(Appreciation appreciationModel);

    Appreciation findAppreciationById(long appreciationId);

    boolean deleteAppreciation(long appreciationId);
}
