package com.kosarict.dao;

import com.kosarict.entity.AppreciationAttachment;

import java.util.List;

/**
 * Created by Younes on 17/12/2016.
 */
public interface AppreciationAttachmentDao {
    List<AppreciationAttachment> getAppreciationAttachmentListByAppreciationId(long appreciationId);

    long saveAppreciationAttachment(AppreciationAttachment appreciationAttachmentModel);
}
