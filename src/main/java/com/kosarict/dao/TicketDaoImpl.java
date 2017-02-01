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
        query.setParameter("finishStatus",(short)3);

        return query.getResultList();
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

    @Override
    public List<Ticket> getTop10Ticket()

    {
        String queryString = "SELECT ticket FROM Ticket ticket WHERE ticket.enable = true order by ticket.id desc ";


        Query query = entityManager.createQuery(queryString);

        return query.getResultList().subList(0, 10);
    }

    @Override
    public List<Ticket> getTicketArchiveList()

    {
        String queryString = "SELECT ticket FROM Ticket ticket WHERE  " +
                "ticket.enable = true and  ticket.ticketStatus.id =:finishStatus";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("finishStatus",(short)3);

        return query.getResultList();
    }
}
