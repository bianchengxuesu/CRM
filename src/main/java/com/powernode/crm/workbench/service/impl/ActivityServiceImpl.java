package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.utils.SqlSessionUtil;
import com.powernode.crm.vo.PaginationVO;
import com.powernode.crm.workbench.dao.ActivityDao;
import com.powernode.crm.workbench.domain.Activity;
import com.powernode.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    @Override
    public boolean save(Activity a) {
        boolean flag = true;

        int count = activityDao.save(a);

        if(count != 1){

            flag = false;

        }

        return flag;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        //取得total
        int total = activityDao.getTotalByCondition(map);
        //取得dataList
        List<Activity> dataList = activityDao.getActivityListByCondition(map);
        //将total和dataList封装到vo中
        PaginationVO<Activity> vo = new PaginationVO<Activity>();
        vo.setDataList(dataList);
        vo.setTotal(total);

        //返回vo对象
        return vo;
    }
}
