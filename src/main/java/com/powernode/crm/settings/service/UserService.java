package com.powernode.crm.settings.service;

import com.powernode.crm.exception.LoginException;
import com.powernode.crm.settings.domain.User;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;
}
