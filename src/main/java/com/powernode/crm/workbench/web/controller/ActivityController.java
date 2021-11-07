package com.powernode.crm.workbench.web.controller;

import com.powernode.crm.settings.domain.User;
import com.powernode.crm.settings.service.UserService;
import com.powernode.crm.settings.service.impl.UserServiceImpl;
import com.powernode.crm.utils.DateTimeUtil;
import com.powernode.crm.utils.PrintJson;
import com.powernode.crm.utils.ServiceFactory;
import com.powernode.crm.utils.UUIDUtil;
import com.powernode.crm.vo.PaginationVO;
import com.powernode.crm.workbench.domain.Activity;
import com.powernode.crm.workbench.service.ActivityService;
import com.powernode.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入市场活动控制器");

//        String path = req.getContextPath();
        String path = req.getServletPath();

        System.out.println("path:"+path);

        if ("/workbench/activity/getUserList.do".equals(path)) {

            //获取用户列表
            getUserList(req,resp);

        }else if("/workbench/activity/save.do".equals(path)){

            save(req,resp);
            
        }else if("/workbench/activity/pageList.do".equals(path)){

            pageList(req,resp);

        }else if("/workbench/activity/delete.do".equals(path)){

            delete(req,resp);

        }else if("/workbench/activity/getUserListAndActivity.do".equals(path)){

            getUserListAndActivity(req,resp);

        }else if("/workbench/activity/update.do".equals(path)){

            update(req,resp);

        }else if("/workbench/activity/detail.do".equals(path)){

            detail(req,resp);

        }
    }

    private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("进入到跳转到详细信息页的操作");

        String id = req.getParameter("id");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity a = as.detail(id);

        System.out.println(a);

        req.setAttribute("a",a);

        req.getRequestDispatcher("/workbench/activity/detail.jsp").forward(req,resp);

    }

    private void update(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("执行市场活动的修改操作");

        String id = req.getParameter("id");
        String owner = req.getParameter("owner");
        String name = req.getParameter("name");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String cost = req.getParameter("cost");
        String description = req.getParameter("description");
        //修改时间，当前系统时间
        String editTime = DateTimeUtil.getSysTime();
        //修改人：当前登录用户
        String editBy = ((User)req.getSession().getAttribute("user")).getName();

        Activity a = new Activity();
        a.setId(id);
        a.setCost(cost);
        a.setStartDate(startDate);
        a.setOwner(owner);
        a.setName(name);
        a.setId(id);
        a.setEndDate(endDate);
        a.setDescription(description);
        a.setEditTime(editTime);
        a.setEditBy(editBy);
        a.setStartDate(startDate);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.update(a);

        PrintJson.printJsonFlag(resp,flag);


    }

    private void getUserListAndActivity(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("进入到查询用户信息列表和根据市场活动信息查询单条");

        String id = req.getParameter("id");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        /*
            分析：返回 uList和vo
            因为此操作
            复用率较低，使用map打包这两信息即可
            Map<String,Object>
         */
        Map<String,Object> map = as.getUserListAndActivity(id);

        PrintJson.printJsonObj(resp,map);

    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("执行市场活动的删除操作");

        String[] ids = req.getParameterValues("id");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.delete(ids);

        PrintJson.printJsonFlag(resp,flag);
    }

    private void pageList(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("进入到查询市场活动信息列表的操作（结合条件查询+分页查询)");

        String name = req.getParameter( "name" );
        String owner = req.getParameter( "owner" );
        String startDate = req.getParameter( "startDate" );
        String endDate = req.getParameter( "endDate");
        String pageNoStr = req.getParameter("pageNo" ) ;
        int pageNo = Integer.valueOf(pageNoStr);
        //每页展现的记录数
        String pagesizeStr = req.getParameter( "pageSize");
        int pageSize = Integer.valueOf(pagesizeStr);
        //计算出略过的记录数
        int skipCount = (pageNo - 1) * pageSize;

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name", name) ;
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        /**
            前端要:市场活动信息列表
            查询的总条数
            业务层拿到了以上两项信息之后，如何做返回map
            map.put( "dataList" : datalist)
            map.put( "total" :total)
            PrintJSON map --> json
            { "total":100 , "datalist":[{市场活动1},{2],{3}]]}

            vo
            PaginationVO<T>
            private int total;
            private List<T> dateList;
            如：
            PaginationVO<Activity> vo = new PaginationVO<>();
            vo.setTotal(total);
            vo.setDataList(dataList);
            PrintJSON vo--> json
            { "total":100 , "datalist":[{市场活动1},{2],{3}]]}


         通过分析，分页查询，每个模块都有
            使用一个通用的vo，操作方便
         */
        PaginationVO<Activity> vo = as.pageList(map);

        PrintJson.printJsonObj(resp, vo);

        

    }

    private void save(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("执行市场活动添加操作");

        String id = UUIDUtil.getUUID();
        String owner = req.getParameter("owner");
        String name = req.getParameter("name");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String cost = req.getParameter("cost");
        String description = req.getParameter("description");
        //创建时间，当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人：当前登录用户
        String createBy = ((User)req.getSession().getAttribute("user")).getName();

        Activity a = new Activity();
        a.setCost(cost);
        a.setStartDate(startDate);
        a.setOwner(owner);
        a.setName(name);
        a.setId(id);
        a.setEndDate(endDate);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);
        a.setStartDate(startDate);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.save(a);

        PrintJson.printJsonFlag(resp,flag);
    }

    private void getUserList(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("获取用户信息");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        PrintJson.printJsonObj(resp,uList);

    }
}
