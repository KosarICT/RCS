package com.kosarict.dao;

import com.kosarict.entity.Appreciation;

/**
 * Created by Admin on 12/16/2016.
 */
public interface AppreciationDao {

    boolean trackingCodeIsExist(String trackingCode);

    long saveAppreciation(Appreciation appreciationModel);
}
