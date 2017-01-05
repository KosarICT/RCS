package com.kosarict.dao;

import com.kosarict.entity.Offer;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Younes on 17/12/2016.
 */
public interface ADOfferDao {
    List<Offer> getAllOfferList();

    @Transactional(isolation = Isolation.SERIALIZABLE)
    long saveOffer(Offer offerModel);

    Offer findOfferById(long offerId);

    @Transactional
    boolean deleteOffer(long offerId);
}
