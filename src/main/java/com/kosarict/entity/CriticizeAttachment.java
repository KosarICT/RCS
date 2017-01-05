package com.kosarict.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Younes on 17/12/2016.
 */
@Entity
public class CriticizeAttachment {
    private int criticizeAttachmentId;
    private long criticizeId;
    private String fileName;
    private String fileType;
    private String date;

    @Id
    @Column(name = "CriticizeAttachment_Id")
    public int getCriticizeAttachmentId() {
        return criticizeAttachmentId;
    }

    public void setCriticizeAttachmentId(int criticizeAttachmentId) {
        this.criticizeAttachmentId = criticizeAttachmentId;
    }

    @Basic
    @Column(name = "Criticize_Id")
    public long getCriticizeId() {
        return criticizeId;
    }

    public void setCriticizeId(long criticizeId) {
        this.criticizeId = criticizeId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CriticizeAttachment that = (CriticizeAttachment) o;

        if (criticizeAttachmentId != that.criticizeAttachmentId) return false;
        if (criticizeId != that.criticizeId) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (fileType != null ? !fileType.equals(that.fileType) : that.fileType != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = criticizeAttachmentId;
        result = 31 * result + (int) (criticizeId ^ (criticizeId >>> 32));
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (fileType != null ? fileType.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
