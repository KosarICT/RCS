package com.kosarict.dao;

import com.kosarict.entity.Tab;

import java.util.List;

/**
 * Created by Sadegh-Pc on 11/30/2016.
 */
public interface TabDao {
    List<Tab> getAllTabList();

    List<Tab> getAllMobileTabList();

    List<Tab> getChildTabByParentId(int tabId);

    List<Tab> getAllTabListByUserId(int userId);

    List<Tab> getAllTabListByRoleId(int roleId);

    List<Tab> getAllTabListByPermissionId(int permissionId);
}
