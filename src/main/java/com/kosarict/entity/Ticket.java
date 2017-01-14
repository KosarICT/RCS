package com.kosarict.entity;

import javax.persistence.*;

/**
 * Created by Ali-Pc on 1/2/2017.
 */

@Entity
@Table(name = "Ticket", schema = "dbo", catalog = "")
public class Ticket {
    private long ticketId;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private String phoneNumber;
    private String mobile;
    private String subject;
    private String description;
    private String submitDate;
    private String email;
    private boolean enable;
    private String trackingCode;
    private TicketType ticketType;
    private ComplaintType complaintType;
    private Hospital hospital;
    private Shift shift;
    private Section section;
    private SendType sendType;
    private Complainant complainant;
    private String persnolFirstName;
    private String persnolLastName;
    private Integer raiting;
    private TicketStatus ticketStatus;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Ticket_Id")
    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    @Basic
    @Column(name = "FirstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "NationalCode")
    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    @Basic
    @Column(name = "PhoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "Mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "Subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "SubmitDate")
    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    @Basic
    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "Enable")
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Basic
    @Column(name = "TrackingCode")
    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    @ManyToOne
    @JoinColumn(name = "ComplainType_Id")
    public ComplaintType getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(ComplaintType complaintType) {
        this.complaintType = complaintType;
    }

    @ManyToOne
    @JoinColumn(name = "Hospital_Id")
    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    @ManyToOne
    @JoinColumn(name = "Shift_Id")
    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    @ManyToOne
    @JoinColumn(name = "Section_Id")
    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    @ManyToOne
    @JoinColumn(name = "SendType_Id")
    public SendType getSendType() {
        return sendType;
    }

    public void setSendType(SendType sendType) {
        this.sendType = sendType;
    }

    @ManyToOne
    @JoinColumn(name = "Complainant_Id")
    public Complainant getComplainant() {
        return complainant;
    }

    public void setComplainant(Complainant complainant) {
        this.complainant = complainant;
    }

    @ManyToOne
    @JoinColumn(name = "TicketType_Id")
    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType= ticketType;
    }

    @Basic
    @Column(name = "PersnolFirstName")
    public String getPersnolFirstName() {
        return persnolFirstName;
    }

    public void setPersnolFirstName(String persnolFirstName) {
        this.persnolFirstName = persnolFirstName;
    }

    @Basic
    @Column(name = "PersnolLastName")
    public String getPersnolLastName() {
        return persnolLastName;
    }

    public void setPersnolLastName(String persnolLastName) {
        this.persnolLastName = persnolLastName;
    }

    @Basic
    @Column(name = "Raiting")
    public Integer getRaiting() {
        return raiting;
    }

    public void setRaiting(Integer raiting) {
        this.raiting = raiting;
    }

    @ManyToOne
    @Column(name = "TicketStatus_Id")
    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

}
