package com.kosarict.dao;

import com.kosarict.entity.OfferAttachment;

import java.util.List;

/**
 * Created by Younes on 17/12/2016.
 */
public interface OfferAttachmentDao {
    List<OfferAttachment> getOfferAttachmentListByOfferId(long offerId);

    long saveOfferAttachment(OfferAttachment offerAttachmentModel);
}
