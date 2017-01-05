package com.kosarict.dao;

import com.kosarict.entity.OfferAttachment;
import com.kosarict.entity.TicketAttachment;

import java.util.List;

/**
 * Created by Ali-Pc on 1/4/2017.
 */
public interface TicketAttachmentDao  {
    List<TicketAttachment> getTicketAttachmentListByTicketId(long ticketId);

    long saveTicketAttachment(TicketAttachment ticketAttachmentModel);
}
