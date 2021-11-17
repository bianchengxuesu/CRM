package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.ClueServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.CustomerServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.TranServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("进入交易控制器");

        String path = req.getServletPath();

        System.out.println("path:"+path);

        if ("/workbench/transaction/getUserList.do".equals(path)) {

            getUserList(req,resp);

        }else if("/workbench/transaction/add.do".equals(path)){

            add(req,resp);
            
        }else if("/workbench/transaction/getContactsListByName.do".equals(path)){

            getContactsListByName(req,resp);

        }else if("/workbench/transaction/getCustomerName.do".equals(path)){

            getCustomerName(req,resp);

        }


    }

    private void getCustomerName(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("取得客户名称列表（按照客户名称进行模糊查询）");

        String name = req.getParameter("name");

        CustomerService customerService = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        List<String> sList = customerService.getCustomerName(name);

        System.out.println(sList.toString());

        PrintJson.printJsonObj(resp,sList);

    }

    private void getContactsListByName(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("进入到查询联系人的操作");

        String cname = req.getParameter("cname");

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());

        List<Contacts> cList = tranService.getContactsListByName(cname);

        PrintJson.printJsonObj(resp,cList);


    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("进入到跳转到交易添加页的操作");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        req.setAttribute("uList",uList);

        System.out.println(uList.toString());

        req.getRequestDispatcher("/workbench/transaction/save.jsp").forward(req,resp);

    }


    private void getActivityListByClueId(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("根据线索id查询关联的市场活动");

        String cludId = req.getParameter("cludId");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> aList = as.getActivityListByClueId(cludId);

        PrintJson.printJsonObj(resp,aList);

    }

    private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("进入到线索详细页面");

        String id = req.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        Clue c = cs.detail(id);

        req.setAttribute("c",c);

        req.getRequestDispatcher("/workbench/clue/detail.jsp").forward(req,resp);

    }

    private void pageList(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("进入列表查询操作");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        List<Clue> clueList = cs.getAllClues();

        PrintJson.printJsonObj(resp,clueList);

    }

    private void save(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("执行线索的添加操作");

    }

    private void getUserList(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("获取用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        PrintJson.printJsonObj(resp,uList);

    }


}
