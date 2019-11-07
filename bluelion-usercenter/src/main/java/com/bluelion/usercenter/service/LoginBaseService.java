package com.bluelion.usercenter.service;

import com.bluelion.usercenter.repository.LoginRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginBaseService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private LoginRepository loginRepository;

    public void clearLoginError(int userId) {
        logger.info("登录错误次数清零");
        if (userId <= 0)
            return;
        loginRepository.clearLoginError(userId);
    }

    public void setLoginError(int userId, int errorCount) {
        logger.info("更新登录错误信息");
        if (userId <= 0)
            return;
        loginRepository.setLoginError(userId, errorCount);
    }

    public void setLastLoginInfo(int userId, String ip) {
        logger.info("更新最后登录信息");
        if (userId <= 0)
            return;
        loginRepository.setLastLoginInfo(userId, ip);
    }
}
