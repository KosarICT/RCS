package com.kosarict.dao;

import com.kosarict.entity.Permission;
import com.kosarict.entity.Role;
import com.kosarict.entity.RolePermission;
import com.kosarict.entity.Users;
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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public List<Permission> getRolePermissionList(int userId) {
        Session session = entityManager.unwrap(Session.class);

        String queryString = "SELECT\n" +
                "\tPermission.*\n" +
                "FROM\n" +
                "\tRolePermission\n" +
                "JOIN Permission ON RolePermission.Permission_Id = Permission.Permission_Id\n" +
                "JOIN UserRole ON UserRole.Role_Id = RolePermission.RoleId\n" +
                "WHERE UserRole.User_Id = " + userId;

        List query = session.createSQLQuery(queryString).addEntity(Permission.class).list();


        return query;
    }


}
