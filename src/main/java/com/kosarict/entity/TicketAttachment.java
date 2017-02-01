package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Ali-Pc on 1/2/2017.
 */
@Entity
@Table(name = "TicketAttachment", schema = "dbo", catalog = "Monitoring")
public class TicketAttachment {
    private int ticketAttachmentId;
    private String fileName;
    private String fileType;
    private String date;
    private long ticketId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TicketAttachment_Id")
    public int getTicketAttachmentId() {
        return ticketAttachmentId;
    }

    public void setTicketAttachmentId(int ticketAttachmentId) {
        this.ticketAttachmentId = ticketAttachmentId;
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
    @Column(name = "Ticket_Id")
    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }


}
