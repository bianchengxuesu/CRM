package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.utils.SqlSessionUtil;
import com.powernode.crm.workbench.dao.ActivityDao;
import com.powernode.crm.workbench.domain.Activity;
import com.powernode.crm.workbench.service.ActivityService;

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
}
