package com.kosarict.dao;

import com.kosarict.entity.Criticize;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Younes on 17/12/2016.
 */
public interface ADCriticismDao {
    List<Criticize> getAllCriticizeList();

    @Transactional(isolation = Isolation.SERIALIZABLE)
    long saveCriticize(Criticize criticizeModel);

    Criticize findCriticizeById(long criticizeId);

    @Transactional
    boolean deleteCriticize(long criticizeId);
}
