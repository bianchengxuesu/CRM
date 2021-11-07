package com.powernode.crm.workbench.service;

import com.powernode.crm.vo.PaginationVO;
import com.powernode.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {
    boolean save(Activity a);

    PaginationVO<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);
}
