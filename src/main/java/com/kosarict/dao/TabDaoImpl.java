package com.kosarict.dao;

import com.kosarict.entity.Tab;
import com.kosarict.entity.TicketUserSeen;
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
 * Created by Sadegh-Pc on 11/30/2016.
 */
@Repository("TabDao")
public class TabDaoImpl implements TabDao {

    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<Tab> getAllTabList() {
        String queryString = "SELECT tab FROM Tab tab WHERE tab.parent = 0 AND tab.enable = true ORDER BY tab.position ASC ";
        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Override
    public List<Tab> getAllMobileTabList() {
        String queryString = "SELECT tab FROM Tab tab WHERE tab.isShowInMobile = true AND tab.enable = true ORDER BY tab.position ASC ";
        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Override
    public List<Tab> getChildTabByParentId(int tabId) {
        String queryString = "SELECT tab FROM Tab tab WHERE tab.parent=:tabId AND tab.enable = true ORDER BY tab.position ASC ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("tabId",tabId);

        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public List<Tab> getAllTabListByUserId(int userId) {
        Session session = entityManager.unwrap(Session.class);


        String queryString="SELECT Tab.Enable,Tab.Icon,Tab.IsShowInMobile,Tab.Name,Tab.Parent,Tab.Position,Tab.Tab_Id,Tab.Title,Tab.Url" +
                "  FROM UsersHospitalSection" +
                "  join HospitalSection ON HospitalSection.HospitalSection_Id=UsersHospitalSection.HospitalSection_Id" +
                "  join Section ON Section.Section_Id=HospitalSection.Section_Id" +
                "  join SectionPermission ON SectionPermission.Section_Id=Section.Section_Id" +
                "  join Permission ON SectionPermission.Permission_Id=Permission.Permission_Id" +
                "  join TabPermission ON TabPermission.Permission_Id=Permission.Permission_Id" +
                "  join Tab ON Tab.Tab_Id=TabPermission.Tab_Id" +
                "  where UsersHospitalSection.User_Id="+userId+" AND Tab.Parent = 0 AND Tab.Enable=1 And Section.Enable=1 And Permission.Enable=1" +
                "  order by Tab.Position";
        List query =
                session.createSQLQuery(queryString).addEntity(Tab.class).list();

        return query;
    }

    @Override
    public List<Tab> getAllTabListByRoleId(int roleId) {
        return null;
    }

    @Override
    public List<Tab> getAllTabListByPermissionId(int permissionId) {
        return null;
    }

    @Override
    public int getNumberOfNew(short tabId, int userId) {
        String queryString = "SELECT ticketUserSeen FROM TicketUserSeen ticketUserSeen WHERE ticketUserSeen.ticket.ticketType.tab.tabId=:tabId AND " +
                "ticketUserSeen.user.userId=:userId";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("userId",userId);
        query.setParameter("tabId",tabId);
        List<TicketUserSeen> ticketUserSeenList=query.getResultList();
        return ticketUserSeenList.size();

    }
}
