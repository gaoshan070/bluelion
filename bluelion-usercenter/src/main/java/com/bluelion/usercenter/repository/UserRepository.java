package com.bluelion.usercenter.repository;

import com.bluelion.shared.model.User;
import com.bluelion.shared.model.UserToken;
import com.bluelion.usercenter.mapper.IUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private IUserMapper userMapper;

    public void addUser(User user, String appId) {
        user.setAppId(appId);
        userMapper.addUser(user);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
    public void addUserDetail(Integer userId, String ip) {
        userMapper.addUserDetail(userId, ip);
    }

    public User login(String username, String password) {
        return userMapper.login(username, password);
    }

    public User detail(Integer userId) {
        return userMapper.detail(userId);
    }

    public User getDetailByEmail(String email) {
        Integer userId = getIdByEmail(email);
        if (userId == null)
            return null;
        return userMapper.detail(userId);
    }

    private Integer getIdByEmail(String email) {
        return userMapper.getIdByEmail(email);
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

    public void clearLoginError(int userId) {
        userMapper.clearLoginError(userId);
    }

    public int updatePassword(int userId, String encodedPassword, String salt) {
        return userMapper.updatePassword(userId, encodedPassword, salt);
    }

    private boolean existUserToken(Integer userId) {
        int count = userMapper.existUserToken(userId);
        return count > 0;
    }
}
