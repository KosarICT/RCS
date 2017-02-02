package com.kosarict.dao;

import com.kosarict.entity.*;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Ali-Pc on 1/2/2017.
 */
@Repository("TicketDao")
public class TicketDaoImpl implements TicketDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<Ticket> getAllTicketList() {
        String queryString = "SELECT ticket FROM Ticket ticket WHERE ticket.enable = true ";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Override
    public List<Ticket> getTicketListByTicketTypeId(short ticketTypeId)

    {
        String queryString = "SELECT ticket FROM Ticket ticket WHERE ticket.ticketType.ticketTypeId =:ticketTypeId " +
                "and ticket.enable = true and  ticket.ticketStatus.id !=:finishStatus";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("ticketTypeId", ticketTypeId);
        query.setParameter("finishStatus", (short) 3);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public List<Ticket> getTicketListByTicketTypeId(short ticketTypeId, int userId) {
        Session session = entityManager.unwrap(Session.class);

        String queryString = "Select distinct \n" +
                "\tTicket.* \n" +
                "FROM  \n" +
                "\tTicket \n" +
                "JOIN TicketErrand ON Ticket.Ticket_Id = TicketErrand.Ticket_Id\n" +
                "JOIN TicketStatus ON Ticket.TicketStatus_Id = TicketStatus.TicketStatus_Id \n" +
                "JOIN TicketType ON Ticket.TicketType_Id = TicketType.TicketType_Id \n" +
                "WHERE  \n" +
                "\tTicket.TicketType_Id = " + ticketTypeId + " AND  \n" +
                "\tTicketErrand.AssignedUser_Id = " + userId + " AND  \n" +
                "\tTicketStatus.TicketStatus_Id != 3 \n" +
                "ORDER BY  \n" +
                "\tTicket.Ticket_Id DESC";

        List query = session.createSQLQuery(queryString).addEntity(Ticket.class).list();


        return query;
    }

    @Override
    public List<TicketErrand> getTicketListByUserId(int userId) {
        String queryString = "SELECT DISTINCT ticketErrand FROM TicketErrand ticketErrand " +
                "JOIN ticketErrand.ticket ticket " +
                "WHERE ticket.enable = true " +
                "AND ticketErrand.assignedUser.userId =:userId ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("userId", userId);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)

    @Override
    public long saveTicket(Ticket ticketModel) {
        Ticket ticket = entityManager.merge(ticketModel);
        return ticket.getTicketId();
    }

    @Override
    public Ticket findTicketById(long ticketId) {
        return entityManager.find(Ticket.class, ticketId);
    }

    @Override
    public boolean deleteTicket(long complainId) {
        Ticket ticket = findTicketById(complainId);
        ticket.setEnable(false);
        saveTicket(ticket);

        return true;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public List<UsersHospitalSection> forwardTicket(int hospitalId, int sectionId) {
        Session session = entityManager.unwrap(Session.class);

        String queryString = "select \n" +
                "\tUsersHospitalSection.*\n" +
                "From \n" +
                "\tUsersHospitalSection\n" +
                "JOIN\n" +
                "\tHospitalSection On UsersHospitalSection.HospitalSection_Id = HospitalSection.HospitalSection_Id\n" +
                "WHERE\n" +
                "\tHospitalSection.Section_Id = " + sectionId + " AND\n" +
                "\tHospitalSection.Hospital_Id = " + hospitalId;


        List query = session.createSQLQuery(queryString).addEntity(UsersHospitalSection.class).list();


        return query;
    }

    @Override
    public boolean trackingCodeIsExist(String trackingCode) {
        try {
            boolean isExist = false;

            String queryString = "SELECT ticket FROM Ticket ticket WHERE ticket.trackingCode =:trackingCode AND ticket.enable = true ";

            Query query = entityManager.createQuery(queryString);
            query.setParameter("trackingCode", trackingCode);

            List<Ticket> ticketList = query.getResultList();

            if (ticketList.size() > 0) {
                isExist = true;
            }

            return isExist;
        } catch (Exception ex) {
            return false;
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public List<Ticket> getTop10Ticket(Users users) {
        Session session = entityManager.unwrap(Session.class);

        String queryString = "SELECT distinct top 10 [Ticket_Id],Ticket.[TicketType_Id],[ComplainType_Id],Ticket.[Hospital_Id],[Shift_Id],Ticket.[Section_Id]" +
                "      ,[SendType_Id],[Complainant_Id],[TicketStatus_Id],[FirstName],[LastName],[NationalCode],[PhoneNumber]" +
                "      ,[Mobile],[PersnolFirstName],[PersnolLastName],[Subject],Ticket.[Description],[Raiting],[SubmitDate]" +
                "      ,[Email],Ticket.Enable ,[TrackingCode] FROM [Monitoring].[dbo].[Ticket]" +
                "  join TicketType on TicketType.TicketType_Id=Ticket.TicketType_Id" +
                "  join Tab on Tab.Tab_Id=TicketType.Tab_Id join TabPermission on TabPermission.Tab_Id=Tab.Tab_Id" +
                "  join Permission on Permission.Permission_Id=TabPermission.Permission_Id" +
                "  join SectionPermission on SectionPermission.Permission_Id=Permission.Permission_Id" +
                "  join Section on Section.Section_Id =SectionPermission.Section_Id" +
                "  join HospitalSection on HospitalSection.Section_Id=Section.Section_Id" +
                "  join UsersHospitalSection on UsersHospitalSection.HospitalSection_Id=HospitalSection.HospitalSection_Id" +
                "  where Ticket.Enable=1 AND Ticket.TicketStatus_Id != 3 and Permission.Enable=1 and Tab.Enable=1 and HospitalSection.Enable=1 and UsersHospitalSection.User_Id=" + users.getUserId();

        List query = session.createSQLQuery(queryString).addEntity(Ticket.class).list();

        return query;
    }

    @Override
    public List<Ticket> getTicketArchiveList() {
        String queryString = "SELECT ticket FROM Ticket ticket WHERE " +
                "ticket.enable = true and  ticket.ticketStatus.id =:finishStatus";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("finishStatus", (short) 3);

        return query.getResultList();
    }
}
