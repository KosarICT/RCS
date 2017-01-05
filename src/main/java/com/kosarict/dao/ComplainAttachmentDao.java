package com.kosarict.dao;

import com.kosarict.entity.ComplainAttachment;

import java.util.List;

/**
 * Created by Sadegh-Pc on 12/6/2016.
 */
public interface ComplainAttachmentDao {
    List<ComplainAttachment> getComplainAttachmentListByComplainId(long complainId);

    long saveComplainAttachment(ComplainAttachment complainAttachmentModel);
}
