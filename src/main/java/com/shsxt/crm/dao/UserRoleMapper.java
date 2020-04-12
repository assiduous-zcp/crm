package com.shsxt.crm.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {

    public Integer countUserRoleByUserId(Integer userId);

    public Integer deleteUserRoleByUserId(Integer userId);

}