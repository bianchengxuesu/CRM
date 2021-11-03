package com.powernode.crm.settings.web.controll;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入用户控制器");
        String path = req.getServletPath();

        if ("/setting/user/xxx.do".equals(path)){

        }else if("/setting/user/other.do".equals(path)){

        }
    }
}
