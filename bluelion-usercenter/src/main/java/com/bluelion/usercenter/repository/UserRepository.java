package com.bluelion.usercenter.repository;

import com.bluelion.usercenter.entity.User;
import com.bluelion.usercenter.mapper.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private IUserMapper userMapper;

    public void addUser() {
        userMapper.addUser();
    }

    public User login(String username, String password) {
        return userMapper.login(username, password);
    }

    public User detail(String account) {
        return userMapper.detail(account);
    }
}
