package com.kosarict.dao;

import com.kosarict.entity.Offer;

/**
 * Created by Ali-Pc on 12/15/2016.
 */
public interface OfferDao {

    boolean trackingCodeIsExist(String trackingCode);

    long saveOffer(Offer offerModel);
}
