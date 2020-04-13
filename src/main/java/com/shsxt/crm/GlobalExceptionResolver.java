package com.shsxt.crm;

import com.alibaba.fastjson.JSON;
import com.shsxt.crm.exceptions.AuthFailedException;
import com.shsxt.crm.exceptions.NoLoginException;
import com.shsxt.crm.exceptions.ParamsException;
import com.shsxt.crm.model.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        ModelAndView mv=new ModelAndView();
        /**
         * 首先判断异常类型
         *      如果异常类型为未登录异常    执行视图的转发
         *
         */
        if (e instanceof NoLoginException){
            NoLoginException ne= (NoLoginException) e;
            mv.setViewName("no_login");
            mv.addObject("msg",ne.getMsg());
            mv.addObject("code",ne.getCode());
            mv.addObject("ctx",httpServletRequest.getContextPath());
            return mv;
        }


        /**
         * 方法返回值类型判断
         *      如果方法级别存在@ResponseBody   方法响应内容为json 否则就是视图
         *      o   参数类型为 HandlerMethod
         * 返回值
         *      视图：默认错误页面
         *
         *
         *      json：错误的json信息
         */

        mv.setViewName("error");
        mv.addObject("code",400);
        mv.addObject("msg","系统异常，请稍后再试！");

        if(o instanceof HandlerMethod){
            HandlerMethod hm= (HandlerMethod) o;
            ResponseBody responseBody = hm.getMethod().getDeclaredAnnotation(ResponseBody.class);
            if (null==responseBody){
                /**
                 * 方法返回视图
                 */
                if (e instanceof ParamsException){
                    ParamsException pe= (ParamsException) e;
                    mv.addObject("msg",pe.getMsg());
                    mv.addObject("code",pe.getCode());
                }
                if (e instanceof AuthFailedException){
                    AuthFailedException pe= (AuthFailedException) e;
                    mv.addObject("msg",pe.getMsg());
                    mv.addObject("code",pe.getCode());
                }
                return mv;

            }else{
                /**
                 * 方法返回json
                 */
                ResultInfo resultInfo=new ResultInfo();
                resultInfo.setCode(300);
                resultInfo.setMsg("系统错误，请稍后再试！");

                if (e instanceof ParamsException){
                    ParamsException pe= (ParamsException) e;
                    resultInfo.setCode(pe.getCode());
                    resultInfo.setMsg(pe.getMsg());
                }
                if (e instanceof AuthFailedException){
                    AuthFailedException pe= (AuthFailedException) e;
                    resultInfo.setCode(pe.getCode());
                    resultInfo.setMsg(pe.getMsg());
                }
                httpServletResponse.setCharacterEncoding("utf-8");
                httpServletResponse.setContentType("application/json;charset=utf-8");
                PrintWriter pw=null;
                try {
                    pw=httpServletResponse.getWriter();
                    pw.write(JSON.toJSONString(resultInfo));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }finally {
                    if (null!=pw){
                        pw.close();
                    }
                }
                return null;
            }

        }else{
            return mv;
        }



    }
}
