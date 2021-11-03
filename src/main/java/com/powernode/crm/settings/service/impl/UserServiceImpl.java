package com.powernode.crm.settings.service.impl;

import com.powernode.crm.settings.dao.UserDao;
import com.powernode.crm.settings.service.UserService;
import com.powernode.crm.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

}
