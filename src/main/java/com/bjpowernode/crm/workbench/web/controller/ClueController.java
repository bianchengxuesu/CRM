package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("进入线索控制器");

        String path = req.getServletPath();

        System.out.println("path:"+path);

        if ("/workbench/clue/getUserList.do".equals(path)) {

            getUserList(req,resp);

        }else if("/workbench/clue/save.do".equals(path)){

            save(req,resp);
            
        }else if("/workbench/clue/pageList.do".equals(path)){

            pageList(req,resp);

        }else if("/workbench/clue/detail.do".equals(path)){

            detail(req,resp);

        }else if("/workbench/clue/getActivityListByClueId.do".equals(path)){

            getActivityListByClueId(req,resp);

        }else if("/workbench/clue/unbund.do".equals(path)){

            unbund(req,resp);

        }else if("/workbench/clue/getActivityListByNameAndNotByClueId.do".equals(path)){

            getActivityListByNameAndNotByClueId(req,resp);

        }else if("/workbench/clue/bund.do".equals(path)){

            bund(req,resp);

        }else if("/workbench/clue/getActivityListByName.do".equals(path)){

            getActivityListByName(req,resp);

        }else if("/workbench/clue/convert.do".equals(path)){

            convert(req,resp);

        }


    }

    private void convert(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("执行线索转换的操作");

        String clueId = req.getParameter("clueId");

        //接收是否需要创建交易的标记
        String flag = req.getParameter("flag");

        if("a".equals(flag)){

            //接收交易表单的参数


        }

    }

    private void getActivityListByName(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("查询市场活动列表（根据名称模糊查询）");

        String aname = req.getParameter("aname");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> aList = as.getActivityListByName(aname);

        PrintJson.printJsonObj(resp,aList);

    }

    private void bund(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("执行关联市场活动的操作");

        String cid = req.getParameter("cid");

        String[] aids = req.getParameterValues("aid");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.bund(cid,aids);

        PrintJson.printJsonFlag(resp,flag);

    }

    private void getActivityListByNameAndNotByClueId(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("查询市场活动列表（根据名称模糊查询+排除已经关联指定线索的列表");

        String aname = req.getParameter("aname");

        String clueId = req.getParameter("clueId");

        Map<String,String> map = new HashMap<String, String>();

        map.put("aname",aname);
        map.put("clueId",clueId);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> aList = as.getActivityListByNameAndNotByClueId(map);

        PrintJson.printJsonObj(resp,aList);

    }

    private void unbund(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("执行解除关联操作");

        String id = req.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.unbund(id);

        PrintJson.printJsonFlag(resp,flag);


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


        String id = UUIDUtil.getUUID();
        String fullname = req.getParameter("fullname");
        String appellation = req.getParameter("appellation");
        String owner = req.getParameter("owner");
        String company = req.getParameter("company");
        String job = req.getParameter("job");
        String website = req.getParameter("website");
        String mphone = req.getParameter("mphone");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String state = req.getParameter("state");
        String source = req.getParameter("source");
        String description = req.getParameter("description");
        String contactSummary = req.getParameter("contactSummary");
        String nextContactTime = req.getParameter("nextContactTime");
        String address = req.getParameter("address");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)req.getSession().getAttribute("user")).getName();

        Clue c = new Clue();

        c.setAddress(address);
        c.setAppellation(appellation);
        c.setCompany(company);
        c.setContactSummary(contactSummary);
        c.setCreateBy(createBy);
        c.setCreateTime(createTime);
        c.setDescription(description);
        c.setEmail(email);
        c.setFullname(fullname);
        c.setId(id);
        c.setJob(job);
        c.setMphone(mphone);
        c.setNextContactTime(nextContactTime);
        c.setOwner(owner);
        c.setPhone(phone);
        c.setSource(source);
        c.setState(state);
        c.setWebsite(website);

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.save(c);

        PrintJson.printJsonFlag(resp,flag);


    }

    private void getUserList(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("获取用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        PrintJson.printJsonObj(resp,uList);

    }


}
