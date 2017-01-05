package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Younes on 17/12/2016.
 */
@Entity
@Table(name = "OfferAttachment", schema = "dbo", catalog = "Monitoring")
public class OfferAttachment {
    private int offerAttachmentId;
    private String fileName;
    private String fileType;
    private String date;
    private long offerId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OfferAttachment_Id")
    public int getOfferAttachmentId() {
        return offerAttachmentId;
    }

    public void setOfferAttachmentId(int offerAttachmentId) {
        this.offerAttachmentId = offerAttachmentId;
    }

    @Basic
    @Column(name = "FileName")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "FileType")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Basic
    @Column(name = "Date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Basic
    @Column(name = "Offer_Id")
    public long getOfferId() {
        return offerId;
    }

    public void setOfferId(long offerId) {
        this.offerId = offerId;
    }
}
