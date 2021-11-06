package com.powernode.crm.workbench.dao;

import com.powernode.crm.settings.domain.User;
import com.powernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    //User login(Map<String, String> map);

    int save(Activity a);

    int getTotalByCondition(Map<String, Object> map);

    List<Activity> getActivityListByCondition(Map<String, Object> map);
}
