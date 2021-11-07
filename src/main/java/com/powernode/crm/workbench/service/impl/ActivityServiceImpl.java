package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.utils.SqlSessionUtil;
import com.powernode.crm.vo.PaginationVO;
import com.powernode.crm.workbench.dao.ActivityDao;
import com.powernode.crm.workbench.dao.ActivityRemarkDao;
import com.powernode.crm.workbench.domain.Activity;
import com.powernode.crm.workbench.domain.ActivityRemark;
import com.powernode.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);

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

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;

        //查询出需要删除备注的记录数
        int count1 = activityRemarkDao.getCountByAids(ids);

        //删除备注，返回收到影响的条数（实际删除数量）
        int count2 = activityRemarkDao.deleteByAids(ids);

        if (count1 != count2){

            flag = false;

        }

        //删除市场活动
        int count3 = activityDao.delete(ids);

        if(count3 != ids.length){
            flag = false;
        }

        return flag;
    }
}
