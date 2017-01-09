package com.kosarict.dao;

import com.kosarict.entity.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Ali-Pc on 1/6/2017.
 */
@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Override
    public List<Role> getRoles() {
        String queryString = "SELECT rol FROM Role rol WHERE rol.enable = true ";

        Query query = entityManager.createQuery(queryString);

        return query.getResultList();
    }

    @Override
    public Role getRole(short roleId) {
        return entityManager.find(Role.class,roleId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public short saveRole(Role roleModel){
        Role role=entityManager.merge(roleModel);

        return role.getRoleId();
    }
}
