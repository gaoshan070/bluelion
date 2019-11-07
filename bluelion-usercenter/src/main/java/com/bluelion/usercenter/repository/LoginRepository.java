package com.bluelion.usercenter.repository;

import com.bluelion.usercenter.mapper.ILoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository {

    @Autowired
    private ILoginMapper loginMapper;

    public void clearLoginError(int userId) {
        loginMapper.clearLoginError(userId);
    }

    public void setLoginError(int userId, int errorCount) {
        loginMapper.setLoginError(userId, errorCount);
    }

    public void setLastLoginInfo(int userId, String ip) {
        loginMapper.setLastLoginInfo(userId, ip);
    }

}
