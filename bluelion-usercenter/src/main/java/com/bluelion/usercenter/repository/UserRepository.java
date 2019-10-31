package com.bluelion.usercenter.repository;

import com.bluelion.usercenter.entity.User;
import com.bluelion.usercenter.entity.UserToken;
import com.bluelion.usercenter.mapper.IUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private IUserMapper userMapper;

    public void addUser(User user) {
        userMapper.addUser(user.getUserName(), user.getNickName(), user.getPassword(), user.getSalt(),user.getEmail());
    }

    public User login(String username, String password) {
        return userMapper.login(username, password);
    }

    public User detail(Integer userId) {
        return userMapper.detail(userId);
    }

    public UserToken getUserToken(Integer userId, long expiredTime) {
        if (userId <= 0)
            return null;
        return userMapper.getUserToken(userId, expiredTime);
    }

    public String getUserVToken(int userId) {
        return userMapper.getUserVToken(userId);
    }

    public boolean addOrUpdateUserToken(Integer userId, String token, String validateToken, String device,
                                        String requestSource) {
        if (userId == null || userId <= 0 || StringUtils.isEmpty(token) || StringUtils.isEmpty(validateToken)) {
            return false;
        }
        if (existUserToken(userId)) {
            userMapper.updateUserToken(userId, validateToken, token, device, requestSource);
        } else {
            userMapper.insertUserToken(userId, validateToken, token, device, requestSource);
        }
        return true;
    }

    public boolean updateUserValidateToken(Integer userId, String validateToken) {
        if (userId == null || userId <= 0 || StringUtils.isEmpty(validateToken)) {
            return false;
        }
        int count = userMapper.updateUserValidateToken(validateToken, userId);
        return count == 1;
    }

    public int clearUserToken(Integer userId) {
        if (userId == null || userId <= 0)
            return 0;
        return userMapper.clearUserToken(userId);
    }

    private boolean existUserToken(Integer userId) {
        int count = userMapper.existUserToken(userId);
        return count > 0;
    }
}
