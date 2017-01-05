package com.kosarict.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Younes on 17/12/2016.
 */
@Entity
public class AppreciationAttachment {
    private long appreciationAttachmentId;
    private String fileName;
    private String fileType;
    private String date;
    private long appreciationId;

    @Id
    @Column(name = "AppreciationAttachment_Id")
    public long getAppreciationAttachmentId() {
        return appreciationAttachmentId;
    }

    public void setAppreciationAttachmentId(long appreciationAttachmentId) {
        this.appreciationAttachmentId = appreciationAttachmentId;
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
    @Column(name = "Appreciation_Id")
    public long getAppreciationId() {
        return appreciationId;
    }

    public void setAppreciationId(long appreciationId) {
        this.appreciationId = appreciationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppreciationAttachment that = (AppreciationAttachment) o;

        if (appreciationId != that.appreciationId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (appreciationId ^ (appreciationId >>> 32));
    }
}
