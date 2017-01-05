package com.kosarict.controller;

import com.kosarict.dao.*;
import com.kosarict.entity.*;
import com.kosarict.model.Constant;
import com.kosarict.tools.PersianCalendar;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by Ali-Pc on 1/2/2017.
 */
@Controller
public class TicketController {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private RelationDao relationDao;

    @Autowired
    private ComplainTypeDao complainTypeDao;

    @Autowired
    private ShiftDao shiftDao;

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private HospitalDao hospitalDao;

    @Autowired
    private ComplainantDao complainantDao;

    @Autowired
    private SendTypeDao sendTypeDao;

    @Autowired
    private ComplainantRelationDao complainantRelationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private TicketErrandDao ticketErrandDao;

    @Autowired
    private TicketAttachmentDao ticketAttachmentDao;

    @Autowired
    private  TicketTypeDao ticketTypeDao;

    @RequestMapping(value = "/ticket/api/saveTicket", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveTicket(@RequestBody String model) {
        try {
            JSONArray jsonArray = new JSONArray(model);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            long ticketId = jsonObject.getLong("ticketId");
            Integer ticketTypeId = jsonObject.getInt("ticketTypeId");
            int hospitalId = jsonObject.getInt("hospitalId");
            int sectionId = jsonObject.getInt("sectionId");
            String shiftId="0";
            if(ticketTypeId!=Constant.Offer)
                shiftId = jsonObject.getString("shiftId").matches("") ? "0" : jsonObject.getString("shiftId");
            Integer compainer=0;

            String sickName = jsonObject.getString("name");
            String sickFamily = jsonObject.getString("family");
            String sickNationalCode = jsonObject.getString("nationalCode");
            String sickTel = jsonObject.getString("tel");
            String sickMobile = jsonObject.getString("mobile");

            String complainSubject = jsonObject.getString("subject");
            String complainDescription = jsonObject.getString("description");
            String complainEmail = jsonObject.getString("email");
            String fileName = jsonObject.getString("fileName");

            PersianCalendar persianCalendar = new PersianCalendar();
            String currentDate = persianCalendar.getIranianSimpleDate();

            Ticket ticket = new Ticket();
            Hospital hospital = hospitalDao.findHospitalById(hospitalId);
            Section section = sectionDao.findSectionById(sectionId);
            Shift shift = shiftDao.findShiftById(Short.parseShort(shiftId));

            TicketType ticketType = ticketTypeDao.getTicketType(ticketTypeId.shortValue());
            SendType sendType = sendTypeDao.findSendTypeById((short) Constant.SendTypeSite);

            Integer trackingNumber = trackingNumber();

            ticket.setTicketId(ticketId);
            ticket.setHospital(hospital);
            ticket.setSection(section);
            ticket.setSendType(sendType);

            ticket.setShift(shift);
            ticket.setFirstName(sickName);
            ticket.setLastName(sickFamily);
            ticket.setNationalCode(sickNationalCode);
            ticket.setPhoneNumber(sickTel);
            ticket.setMobile(sickMobile);

            ticket.setSubject(complainSubject);
            ticket.setDescription(complainDescription);
            ticket.setSubmitDate(currentDate);
            ticket.setEmail(complainEmail);
            ticket.setTrackingCode(trackingNumber.toString());
            ticket.setTicketType(ticketType);
            ticket.setEnable(true);

            if(ticketTypeId== Constant.Complaint){
                compainer = jsonObject.getInt("compainer");
                Complainant complainant = complainantDao.findComplainantById(compainer.shortValue());
                Integer complaintTypeId = jsonObject.getInt("complaintTypeId");
                ComplaintType complaintType = complainTypeDao.findComplaintTypeById(complaintTypeId.shortValue());
                ticket.setComplaintType(complaintType);
                ticket.setComplainant(complainant);
            }else if(ticketTypeId==Constant.Appereciation){
                String appreciationUserName = jsonObject.getString("appreciationUserName");
                String appreciationUserFamily = jsonObject.getString("appreciationUserFamily");
                ticket.setPersnolFirstName(appreciationUserName);
                ticket.setPersnolLastName(appreciationUserFamily);
            }

            long newTicketId = ticketDao.saveTicket(ticket);

            if(ticketTypeId==Constant.Complaint) {
                if (compainer == 2) {
                    String compalainerName = jsonObject.getString("compalainerName");
                    String compalainerFamily = jsonObject.getString("compalainerFamily");
                    String registerNationalCode = jsonObject.getString("registerNationalCode");
                    Integer relationId = jsonObject.getInt("relationId");
                    ComplainantRelation complainantRelation = new ComplainantRelation();

                    complainantRelation.setRelationId(relationId.shortValue());
                    complainantRelation.setComplainId(newTicketId);
                    complainantRelation.setFirstName(compalainerName);
                    complainantRelation.setLastName(compalainerFamily);
                    complainantRelation.setNationalCode(registerNationalCode);

                    complainantRelationDao.saveComplainantRelation(complainantRelation);
                }


                List<UsersHospitalSection> userSections = ticketDao.forwardTicket(hospitalId, sectionId);
                if (userSections.size() > 0) {
                    UsersHospitalSection userSection = userSections.get(0);
                    Users users = userDao.findUserById(userSection.getUser().getUserId());
                    TicketErrand ticketErrand = new TicketErrand();
                    Ticket ticket1 = ticketDao.findTicketById(newTicketId);

                    ticketErrand.setTicket(ticket1);
                    ticketErrand.setAssignedUser(users);
                    ticketErrand.setSubmitDate(currentDate);

                    ticketErrandDao.saveTicketErrand(ticketErrand);
                }

                if (!fileName.matches("")) {
                    TicketAttachment ticketAttachment = new TicketAttachment();
                    ticketAttachment.setFileName(fileName.split("\\.")[0]);
                    ticketAttachment.setDate(currentDate);
                    ticketAttachment.setFileType(fileName.split("\\.")[1]);
                    ticketAttachment.setTicketId(ticketId);

                    ticketAttachmentDao.saveTicketAttachment(ticketAttachment);
                }
            }
            return trackingNumber.toString();

        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }


    private int trackingNumber() {
        Integer trackingNumber = 0;
        while (true) {
            Random rand = new Random();

            trackingNumber = rand.nextInt((1000000 - 100000) + 1) + 100000;

            if (ticketDao.trackingCodeIsExist(trackingNumber.toString())) {
                continue;
            }

            break;
        }
        return trackingNumber;
    }


}
