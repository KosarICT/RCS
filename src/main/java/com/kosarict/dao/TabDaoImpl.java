package com.kosarict.dao;

import com.kosarict.entity.Tab;
import org.springframework.stereotype.Repository;

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

    @Override
    public List<Tab> getAllTabListByUserId(int userId) {
        return null;
    }

    @Override
    public List<Tab> getAllTabListByRoleId(int roleId) {
        return null;
    }

    @Override
    public List<Tab> getAllTabListByPermissionId(int permissionId) {
        return null;
    }
}
