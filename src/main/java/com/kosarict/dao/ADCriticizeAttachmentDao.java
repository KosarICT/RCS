package com.kosarict.dao;

import com.kosarict.entity.CriticizeAttachment;

import java.util.List;

/**
 * Created by Younes on 17/12/2016.
 */
public interface ADCriticizeAttachmentDao {
    List<CriticizeAttachment> getCriticizeAttachmentListByCriticizeId(long criticizeId);
}
