package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入用户控制器"+"123");
        String path = req.getServletPath();

        if ("/settings/user/login.do".equals(path)){
            login(req,resp);
        }else if("/settings/user/other.do".equals(path)){

        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入验证登录操作"+"456");
        String loginAct = req.getParameter("loginAct");
        String loginPwd = req.getParameter("loginPwd");

        //将密码明文转换为MD5的密文
        loginPwd = MD5Util.getMD5(loginPwd);

        //接收浏览器端的IP地址
        String IP = req.getRemoteAddr();
        System.out.println("IP: -- "+IP);

        //未来业务层开发，统一使用代理类形态的接口对象
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        try {
            User user = us.login(loginAct,loginPwd,IP);

            req.getSession().setAttribute("user",user);
            //如果程序执行到此，说明业务层没有为controller抛出异常
            //表示登录成功
            PrintJson.printJsonFlag(resp,true);
        }catch (Exception e) {
            e.printStackTrace();
            //获取返回信息
            String msg = e.getMessage();

            /**
             * 两种手段处理多个信息：
             *  1.创建map
             *  2.创建一个VO
             *  如果展现信息，大量多次重复，创建一个vo类，使用方便
             *  如果展现信息，只在该需求中用到，创建一个map，更方便
             *
             */
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(resp,map);
        }



    }


}
