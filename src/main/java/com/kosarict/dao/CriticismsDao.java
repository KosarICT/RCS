package com.kosarict.dao;

import com.kosarict.entity.Criticize;

/**
 * Created by Admin on 12/15/2016.
 */
public interface CriticismsDao {
    boolean trackingCodeIsExist(String trackingCode);

    long saveCriticize(Criticize criticizeModel);
}
