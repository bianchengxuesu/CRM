package com.powernode.crm.settings.dao;

import com.powernode.crm.settings.domain.User;

import java.util.Map;

public interface UserDao {
    User login(Map<String, String> map);
}
