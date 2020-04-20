package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.dao.UserRoleMapper;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.utils.*;
import com.shsxt.crm.vo.User;
import com.shsxt.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@SuppressWarnings("all")
public class UserService extends BaseService<User,Integer> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    public UserModel login(String userName, String userPwd){
        /**
         * 参数校验
         *      用户密码非空判断
         * 根据用户名查询用户记录
         * 校验用户是否存在
         *      不存在  方法结束
         *      存在    校验密码
         *          密码错误    提示密码不正确 方法结束
         *          密码正确    用户登录成功，返回用户相关信息
         */
        checkLoginParams(userName,userPwd);

        User user=userMapper.queryUserByUserName(userName);
        AssertUtil.isTrue(null==user,"用户已注销或不存在！");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(userPwd))),"用户密码错误！");

        return buildUserModelInfo(user);

    }


    private UserModel buildUserModelInfo(User user) {
        return new UserModel(UserIDBase64.encoderUserID(user.getId()),user.getUserName(),user.getTrueName());
    }

    private void checkLoginParams(String userName, String userPwd) {

        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"用户密码不能为空！");

    }



    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserPassword(Integer userId,String oldPassword,String newPassword,String confirmPassword){
        /**
         * 参数校验
         *      所有参数的非空判断
         *      oldPassword必须和数据库一致
         *      newPassword和confirmPassword必须一致
         *      newPassword和oldPassword不能相同
         * 设置用户新密码加密
         * 执行更新
         */

        checkParams(userId,oldPassword,newPassword,confirmPassword);

        User user = selectByPrimaryKey(userId);
        user.setUserPwd(Md5Util.encode(newPassword));
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"密码修改失败！");


    }

    private void checkParams(Integer userId, String oldPassword, String newPassword, String confirmPassword) {

        User user=selectByPrimaryKey(userId);

        AssertUtil.isTrue(null==userId || null==user,"用户未登录或不存在");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"请输入原密码！");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"请输入新密码！");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword),"请输入确认密码！");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"新密码和确认密码不一致！");

        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(oldPassword))),"原密码有误！");

        AssertUtil.isTrue(oldPassword.equals(newPassword),"新密码不能与原密码相同！");


    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(User user){
        /**
         * 参数校验
         *  非空判断
         *      userName    用户名唯一
         *      email   格式合法
         *      phone   格式合法
         * 设置默认参数
         *      isValid
         *      createDate  updateDate
         *      userPwd 123456->Md5加密
         *
         * 执行添加操作，判断结果
         */
        checkParams(user.getUserName(),user.getEmail(),user.getPhone());
        User temp=userMapper.queryUserByUserName(user.getUserName());
        AssertUtil.isTrue(null!=temp && temp.getIsValid()==1,"该用户已存在！");
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        AssertUtil.isTrue(insertHasKey(user)==null,"用户添加失败！");
        int userId=user.getId();

        /**
         * 用户角色分配
         *      userId
         *      roleIds
         */

        relaionUserRole(userId,user.getRoleIds());
    }

    private void relaionUserRole(int userId, List<Integer> roleIds) {

        /**
         * 用户角色分配
         *      原始角色不存在     添加新的角色记录
         *      原始角色存在      添加新的角色记录
         *      原始角色存在      清空所有角色记录
         *      原始角色存在      移除部分角色
         * 如何进行角色的分配
         *      如果用户原始角色存在  首先清空原始所有角色
         *      添加新的角色记录到用户角色表
         */

        int count=userRoleMapper.countUserRoleByUserId(userId);
        if(count>0){
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId)!=count,"用户角色分配失败！");

        }

        if (null!=roleIds && roleIds.size()>0){
            List<UserRole> userRoles=new ArrayList<UserRole>();
            roleIds.forEach(roleId->{
                UserRole userRole=new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                userRoles.add(userRole);
            });
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoles)<userRoles.size(),"用户角色分配失败！");
        }

    }

    private void checkParams(String userName, String email, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(email),"请输入邮箱的地址！");
        AssertUtil.isTrue(!(EmailUtil.checkEmaile(email)),"邮箱地址不合法！");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(phone)),"手机号格式不合法！");

    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user){
        /**
         * 参数校验
         *  非空判断
         *      id  记录必须存在
         *      userName    用户名唯一
         *      email   格式合法
         *      phone   格式合法
         * 设置默认参数
         *      updateDate
         *
         *
         * 执行更新操作，判断结果
         */
        AssertUtil.isTrue(null==user.getId() || null==selectByPrimaryKey(user.getId()),"待更新记录不存在！");
        checkParams(user.getUserName(),user.getEmail(),user.getPhone());
        User temp=userMapper.queryUserByUserName(user.getUserName());
        if (null!=temp && temp.getIsValid()==1){
            AssertUtil.isTrue(!(user.getId().equals(temp.getId())),"该用户已存在！");
        }

        user.setUpdateDate(new Date());

        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"用户更新失败！");



        relaionUserRole(user.getId(),user.getRoleIds());



    }


    public void deleteUser(Integer userId){
        User user=selectByPrimaryKey(userId);
        AssertUtil.isTrue(null==userId||null==user,"待删除记录不存在！");

        int count=userRoleMapper.countUserRoleByUserId(userId);
        if(count>0){
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId)!=count,"用户角色分配失败！");

        }

        user.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"用户记录删除失败！");

    }



    public List<Map<String, Object>> queryAllCustomerManager(){
        return userMapper.queryAllCustomerManager();
    }


}
