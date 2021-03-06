package com.kosarict.dao;

import com.kosarict.entity.Permission;
import com.kosarict.entity.Role;
import com.kosarict.entity.RolePermission;

import java.util.List;

/**
 * Created by Ali-Pc on 1/6/2017.
 */
public interface RoleDao {
    List<Role> getRoles();

    Role getRole(short roleId);

    short saveRole(Role role);

    List<Permission> getRolePermissionList(int userId);

}
