package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.ClueServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.CustomerServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.TranServiceImpl;

import javax.servlet.ServletContext;
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

        }else if("/workbench/transaction/save.do".equals(path)){

            save(req,resp);

        }else if("/workbench/transaction/pageList.do".equals(path)){

            pageList(req,resp);

        }else if("/workbench/transaction/detail.do".equals(path)){

            detail(req,resp);

        }else if("/workbench/transaction/getHistoryListByTranId.do".equals(path)){

            getHistoryListByTranId(req,resp);

        }


    }

    private void getHistoryListByTranId(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("根据交易id取得相应的历史列表");

        String tranId = req.getParameter("tranId");

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());

        List<TranHistory> thList = tranService.getHistoryListByTranId(tranId);

        //阶段和可能性之间的对应关系
        Map<String,String> pMap = (Map<String, String>) this.getServletContext().getAttribute("pMap");

        //将交易历史列表遍历
        for (TranHistory tranHistory : thList) {

            String stage = tranHistory.getStage();
            String possibility = pMap.get(stage);

            tranHistory.setPossibility(possibility);

        }

        PrintJson.printJsonObj(resp,thList);

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

        System.out.println("进入到交易详细页面");

        String id = req.getParameter("id");

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());

        Tran t = tranService.detail(id);

        String stage = t.getStage();

        //处理阶段对应的可能性
//        ServletContext application = this.getServletContext();
        Map<String,String> pMap = (Map<String, String>) this.getServletContext().getAttribute("pMap");
        String possibility = pMap.get(stage);
        t.setPossibility(possibility);

        req.setAttribute("t",t);
        req.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(req,resp);

    }

    private void pageList(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("进入交易查询操作");

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        List<Tran> tranList = ts.getAllTrans();

        PrintJson.printJsonObj(resp,tranList);

    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("执行交易的添加操作");

        String id = UUIDUtil.getUUID();
        String owner = req.getParameter("owner");
        String money = req.getParameter("money");
        String name = req.getParameter("name");
        String expectedDate = req.getParameter("expectedDate");
        //此处我们暂时用客户名字，还没有id
        String customerName = req.getParameter("customerName");
        String stage = req.getParameter("stage");
        String type = req.getParameter("type");
        String source = req.getParameter("source");
        String activityId = req.getParameter("activityId");
        String contactsId = req.getParameter("contactsId");
        //创建时间，当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人：当前登录用户
        String createBy = ((User)req.getSession().getAttribute("user")).getName();
        /*String editBy = req.getParameter("");
        String editTime = req.getParameter("");*/
        String description = req.getParameter("description");
        String contactSummary = req.getParameter("contactSummary");
        String nextContactTime = req.getParameter("nextContactTime");

        Tran t = new Tran();
        t.setId(id);
        t.setOwner(owner);
        t.setMoney(money);
        t.setName(name);
        t.setExpectedDate(expectedDate);
        t.setStage(stage);
        t.setType(type);
        t.setSource(source);
        t.setActivityId(activityId);
        t.setContactsId(contactsId);
        t.setCreateTime(createTime);
        t.setCreateBy(createBy);
        t.setDescription(description);
        t.setContactSummary(contactSummary);
        t.setNextContactTime(nextContactTime);

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        //在业务层通过名字找id
        boolean flag = ts.save(t,customerName);

        if (flag){

            //如果添加交易成功，跳转到列表页
            resp.sendRedirect(req.getContextPath() + "/workbench/transaction/index.jsp");

        }

    }

    private void getUserList(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("获取用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        PrintJson.printJsonObj(resp,uList);

    }


}
