package com.kosarict.dao;

import com.kosarict.entity.SendType;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Younes on 09/12/2016.
 */
public interface SendTypeDao {
    List<SendType> getAllSendTypeList();

    @Transactional(isolation = Isolation.SERIALIZABLE)
    int saveSendType(SendType sendTypeModel);

    SendType findSendTypeById(short sendTypeId);

    @Transactional
    boolean deleteSendType(short sendTypeId);
}
