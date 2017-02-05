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
    public List<Users> forwardTicket(int hospitalId, int managerRoleId) {
        Session session = entityManager.unwrap(Session.class);

        String queryString = "SELECT users.* FROM Users " +
                "JOIN UserRole ON Users.User_Id=UserRole.User_Id " +
                "JOIN UsersHospitalSection ON Users.User_Id=UsersHospitalSection.User_Id " +
                "JOIN HospitalSection ON UsersHospitalSection.HospitalSection_Id=HospitalSection.HospitalSection_Id " +
                "WHERE UserRole.Role_Id = " + managerRoleId + " AND HospitalSection.Hospital_Id = " + hospitalId;


        List query = session.createSQLQuery(queryString).addEntity(Users.class).list();


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

        String queryString = "SELECT distinct top 10 Ticket.* " +
                "FROM Ticket" +
                "join TicketType on TicketType.TicketType_Id=Ticket.TicketType_Id" +
                "join TicketErrand on TicketErrand.Ticket_Id=Ticket.Ticket_Id" +
                "join UserRole on UserRole.User_Id=TicketErrand.AssignedUser_Id" +
                "join Role on Role.Role_Id=UserRole.User_Id" +
                "join UsersHospitalSection on UsersHospitalSection.User_Id=UserRole.User_Id" +
                "join HospitalSection on HospitalSection.HospitalSection_Id=UsersHospitalSection.HospitalSection_Id" +
                "where Ticket.Enable=1 AND Ticket.TicketStatus_Id != 3 and HospitalSection.Enable=1 and TicketErrand.AssignedUser_Id=" + users.getUserId()+
                "Order by Ticket.SubmitDate DESC" ;

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
