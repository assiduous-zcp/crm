package com.shsxt.crm.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {

    public Integer countPermissionByRoleId(Integer roleId);

    public int deletePermissionByRoleId(Integer roleId);

    List<Integer> queryRoleHasAllModuleIdsByRoleId(Integer roleId);

    List<String> queryUserHasRolesHasPermissions(Integer userId);

    Integer countPermissionByModuleId(Integer mid);
    public Integer deletePermissionByModuleId(Integer mid);

}