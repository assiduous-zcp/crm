package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.UserIDBase64;
import com.shsxt.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends BaseService<User,Integer> {

    @Autowired
    private UserMapper userMapper;

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


}
