package com.shsxt.crm.controller;

import com.github.pagehelper.PageException;
import com.shsxt.base.BaseController;
import com.shsxt.crm.exceptions.ParamsException;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import com.shsxt.crm.utils.UserIDBase64;
import com.shsxt.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * 登录页面
     * @return
     */
    @RequestMapping("index")
    public String index(){
        /*int a=1/0;*/
        /*if (1==1){
            throw new ParamsException("参数异常...");
        }*/
        return "index";
    }


    /**
     * 后端管理主页面
     * @return
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request){
        /*Cookie[] cookies = request.getCookies();
        String value=null;
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("userIdStr")) {
                value = cookie.getValue();
                break;
            }
        }
        request.setAttribute("userName", UserIDBase64.decoderUserID(value));*/
        // 通过工具类得到登录后的用户id
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //通过用户id查询用户信息存到request作用域
        request.setAttribute("user",userService.selectByPrimaryKey(userId));
        return "main";
    }

}
