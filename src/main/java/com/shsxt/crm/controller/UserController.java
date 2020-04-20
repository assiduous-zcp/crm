package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import com.shsxt.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @GetMapping("user/queryUserByUserId")
    @ResponseBody
    public User queryUserByUserId(Integer userId){
        return userService.selectByPrimaryKey(userId);
    }


    /*@PostMapping("user/login")*/
    @RequestMapping("user/login")
    @ResponseBody
    public ResultInfo login(String userName,String userPwd){
        /*try {
            userService.login(userName,userPwd);
        } catch (Exception e) {
            e.printStackTrace();
            //e.getMessage();
        }*/

        /*ResultInfo resultInfo=new ResultInfo();*/

       /* try {*/
            UserModel userModel = userService.login(userName, userPwd);
            /*resultInfo.setResult(userModel);*/
       /* } catch (ParamsException e) {
            e.printStackTrace();
            resultInfo.setMsg(e.getMsg());
            resultInfo.setCode(e.getCode());
        }catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMsg("failed");
            resultInfo.setCode(500);
        }*/
        return success("用户登录成功",userModel);

    }

    /*@PostMapping("user/updatePassword")*/
    @RequestMapping("user/updatePassword")
    @ResponseBody
    public ResultInfo updatePassword(HttpServletRequest request, String oldPassword, String newPassword, String confirmPassword){

        /*ResultInfo resultInfo=new ResultInfo();*/

        /*try {*/
            userService.updateUserPassword(LoginUserUtil.releaseUserIdFromCookie(request),oldPassword,newPassword,confirmPassword);
       /* } catch (ParamsException e) {
            e.printStackTrace();
            resultInfo.setMsg(e.getMsg());
            resultInfo.setCode(e.getCode());
        }catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMsg("failed");
            resultInfo.setCode(500);
        }*/
        return success("密码更新成功");

    }

    @RequestMapping("user/index")
    public String index(){
        return "user";
    }

    @RequestMapping("user/save")
    @ResponseBody
    public ResultInfo saveUser(User user){
        /*user.getRoleIds().forEach(System.out::println);*/
        userService.saveUser(user);
        return success("用户记录添加成功");
    }

    @RequestMapping("user/list")
    @ResponseBody
    public Map<String,Object> queryUsersByParams(UserQuery userQuery){
        return userService.queryByParamsForDataGrid(userQuery);
    }


    @RequestMapping("user/update")
    @ResponseBody
    public ResultInfo updateUser(User user){
        userService.updateUser(user);
        return success("用户记录更新成功");
    }


    @RequestMapping("user/delete")
    @ResponseBody
    public ResultInfo deleteUser(@RequestParam(name = "id") Integer userId){
        userService.deleteUser(userId);
        return success("用户记录删除成功");
    }



    @RequestMapping("user/queryAllCustomerManager")
    @ResponseBody
    public List<Map<String,Object>> queryAllCustomerManager(){
        return userService.queryAllCustomerManager();
    }



}
