package com.kosarict.dao;

import com.kosarict.entity.CriticizeAttachment;

import java.util.List;

/**
 * Created by Admin on 12/19/2016.
 */
public interface CriticizeAttachmentDao {
    List<CriticizeAttachment> getCriticizeAttachmentListByCriticizeId(long criticizeId);

    long saveCriticizeAttachment(CriticizeAttachment criticizeAttachmentModel);
}
