package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Sadegh-Pc on 12/6/2016.
 */
@Entity
public class ComplainAttachment {
    private int complainAttachmentId;
    private long complainId;
    private String fileName;
    private String fileType;
    private String date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ComplainAttachment_Id")
    public int getComplainAttachmentId() {
        return complainAttachmentId;
    }

    public void setComplainAttachmentId(int complainAttachmentId) {
        this.complainAttachmentId = complainAttachmentId;
    }

    @Basic
    @Column(name = "Complain_Id")
    public long getComplainId() {
        return complainId;
    }

    public void setComplainId(long complainId) {
        this.complainId = complainId;
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
}
