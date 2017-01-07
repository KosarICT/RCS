package com.kosarict.dao;

import com.kosarict.entity.Role;
import com.kosarict.entity.UserRole;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Ali-Pc on 1/6/2017.
 */
@Repository("UserRoleDao")
public class UserRoleDaoImpl implements UserRoleDao {

    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<UserRole> getUserRole(int userId) {
        String queryString = "SELECT userRole FROM UserRole userRole  WHERE userRole.users.userId=:userId ";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int save(UserRole userRoleModel) {
        UserRole userRole = entityManager.merge(userRoleModel);

        return userRole.getUserRoleId();
    }

    @Transactional
    @Override
    public boolean deleteUserRole(int userRoleId) {
        UserRole userRole = entityManager.find(UserRole.class, userRoleId);

        entityManager.remove(userRole);

        return true;
    }
}
