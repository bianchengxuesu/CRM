package com.powernode.crm.workbench.dao;

import com.powernode.crm.settings.domain.User;

import java.util.Map;

public interface ActivityRemarkDao {
    User login(Map<String, String> map);
}
