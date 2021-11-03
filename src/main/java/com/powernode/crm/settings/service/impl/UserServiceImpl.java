package com.powernode.crm.settings.service.impl;

import com.powernode.crm.exception.LoginException;
import com.powernode.crm.settings.dao.UserDao;
import com.powernode.crm.settings.domain.User;
import com.powernode.crm.settings.service.UserService;
import com.powernode.crm.utils.DateTimeUtil;
import com.powernode.crm.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String,String> map = new HashMap<String, String>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user = userDao.login(map);

        //查询失败
        if(user==null) {
            throw new LoginException("帐号密码错误");
        }
        //验证其他项
        //验证失效时间
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        //当前时间超过了失效时间
        if(currentTime.compareTo(expireTime)>0){
            throw new LoginException("帐号已失效");
        }
        //判定锁定状态
        String lockState = user.getLockState();
        if("0".equals(lockState)) {
            throw new LoginException("帐号已锁定");
        }
        //判断IP地址
        String allowsIps = user.getAllowIps();
        if(!allowsIps.contains(ip)){
            throw new LoginException("IP地址受限制");
        }
        return user;
    }
}
