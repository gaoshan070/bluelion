package com.bluelion.usercenter.service;

import com.bluelion.shared.constants.UserConstants;
import com.bluelion.shared.model.BaseRequest;
import com.bluelion.shared.model.SafeInfo;
import com.bluelion.shared.model.User;
import com.bluelion.shared.model.UserToken;
import com.bluelion.shared.utils.DateUtil;
import com.bluelion.shared.utils.ValidateUtil;
import com.bluelion.usercenter.cache.IUserCacheManager;
import com.bluelion.usercenter.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBaseService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    IUserCacheManager userCacheManager;

    public boolean createUser(User user, String appId, String ip) {
        userRepository.addUser(user, appId);
        if( user.getId() <= 0) {
            logger.error("插入用户记录失败");
            return false;
        }
        logger.info("插入用户记录成功");
        userRepository.addUserDetail(user.getId(), ip);
        logger.info("插入用户详情记录成功");
        return true;
    }

    public boolean updateUser(User user) {
        userRepository.updateUser(user);
        logger.info("更新用户记录成功");
        return true;
    }

    /**
     * 通过用户ID获取用户详细信息
     *
     * @param userId 用户ID
     * @return 用户对象
     */
    public User getDetailById(Integer userId) {
        if (userId == null)
            return null;
        return userRepository.detail(userId);
    }

    /**
     * 通过email获取用户详细信息
     *
     * @param email 邮箱
     * @return 用户对象
     */
    public User getDetailByEmail(String email) {
        if (StringUtils.isEmpty(email) || !ValidateUtil.isEmail(email))
            return null;
        return userRepository.getDetailByEmail(email);
    }

    /**
     * 检查用户token(客户端用)
     */

    public boolean checkUserToken4Client(int userId, String inputToken, String inputDevice,
                                         SafeInfo safeInfo) throws Exception {
//        LogContext.instance().info("检查客户端令牌逻辑");
        return checkUserToken(userId, inputToken, inputDevice, safeInfo);
    }

    /**
     * 检查用户token
     */
    private boolean checkUserToken(int userId, String inputToken, String inputDevice, SafeInfo safeInfo) {
        if (userId <= 0 || StringUtils.isEmpty(inputToken))
            return false;
        UserToken userToken = getUserTokenObject(userId);
        if (userToken == null) {
            return false;
        }
        boolean isFromClient = StringUtils.isNotEmpty(inputDevice);
        String device = "";
        String tokenRequestSource = "";
        String token = userToken.getToken();
        if (isFromClient) {
            device = userToken.getDevice();
            tokenRequestSource = userToken.getRequestSource();
        }
        boolean checkTokenOnlyByUserId = false;
        if (safeInfo != null && isFromClient) {
            checkTokenOnlyByUserId = checkTokenOnlyByUserId(safeInfo, tokenRequestSource);
        }
        if (isFromClient && !checkTokenOnlyByUserId)
            return inputToken.equals(token) && inputDevice.equals(device);
        else
            return inputToken.equals(token);
    }
    /**
     * 更新token信息
     */
    public boolean updateToken(User user, String device, SafeInfo safeInfo, boolean isRegister) {
//        LogContext.instance().info("更新TOKEN逻辑");
        if (user == null)
            return false;
        if (!isRegister) {
//            LogContext.instance().info("不是注册请求,进入检查是否是同设备和TOKEN不顶号配置检查");
            UserToken userToken = getUserTokenObject(user.getId());
            boolean checkTokenOnlyByUserId = false;
            boolean isNoCheckTokenDevice = false;
            if (userToken != null) {
                checkTokenOnlyByUserId = checkTokenOnlyByUserId(safeInfo, userToken.getRequestSource());
                isNoCheckTokenDevice = StringUtils.isNotEmpty(device) && device.equals(userToken.getDevice());
            }
            if (userToken != null && (isNoCheckTokenDevice || checkTokenOnlyByUserId)) {
//                LogContext.instance().info("不更新TOKEN,更新VTOKEN");
                String vToken = user.generateUserValidateToken();
                boolean updateSuccess = userRepository.updateUserValidateToken(user.getId(), vToken);
                boolean updateCacheSuccess = false;
                if (updateSuccess) {
                    updateCacheSuccess = userCacheManager.setUserValidateToken(user.getId(), vToken);
                    user.setVToken(vToken);
                }
//                LogContext.instance().info("更新VTOKEN缓存结果:" + updateCacheSuccess);
                user.setToken(userToken.getToken());
                return true;
            }
        }
        String token = user.generateUserToken();
        String validateToken = user.generateUserValidateToken();
        boolean updateDBTokenResult = userRepository.addOrUpdateUserToken(user.getId(), token,
                validateToken, device, safeInfo.getRequestSource());
//        LogContext.instance().info("更新DB TOKEN");
        if (!updateDBTokenResult) {
//            LogContext.instance().error("更新DB TOKEN失败");
            return false;
        }
        boolean updateRedisUserTokenResult = userCacheManager.setUserToken(user.getId(), token,
                device, safeInfo.getRequestSource());
        boolean updateRedisUserVTokenResult = userCacheManager.setUserValidateToken(user.getId(), validateToken);
        boolean isSuccess = updateRedisUserTokenResult && updateRedisUserVTokenResult;
        user.setToken(token);
        user.setVToken(validateToken);
//        LogContext.instance().info("更新缓存TOKEN");
        if (!isSuccess) {
//            LogContext.instance().error("更新缓存TOKEN失败");
            return false;
        }
        return true;
    }

    /**
     * 注销token信息
     */
    public boolean deleteToken(int userId) {
        boolean updateDBResult = false;
        try {
//            LogContext.instance().info("删除token逻辑");
//            LogContext.instance().info("清空数据库token");
            int updateCount = userRepository.clearUserToken(userId);
            updateDBResult = updateCount > 0;
//            LogContext.instance().info("db token:" + updateDBResult);
//            LogContext.instance().info("清空缓存服务器token");
            boolean updateRedisUserTokenResult = userCacheManager.deleteUserToken(userId);
            boolean updateRedisUserVTokenResult = userCacheManager.deleteUserValidateToken(userId);
//            LogContext.instance().info("redis token result:" + updateRedisUserTokenResult);
//            LogContext.instance().info("redis vtoken result:" + updateRedisUserVTokenResult);
            boolean result = updateDBResult && updateRedisUserTokenResult && updateRedisUserVTokenResult;
//            LogContext.instance().info("注销token结果:" + result);
        } catch (Exception e) {
//            LogContext.instance().error(e, "清空TOKEN失败");
        }
        return updateDBResult;
    }

    /**
     * 获取用户token对象
     */
    public UserToken getUserTokenObject(int userId) {
//        LogContext.instance().info("获取TOKEN对象逻辑");
        UserToken userTokenFromRedis = userCacheManager.getUserToken(userId);
        if (userTokenFromRedis != null) {
//            LogContext.instance().info("从缓存读取TOKEN对象");
            UserToken userToken = new UserToken();
            userToken.setToken(userTokenFromRedis.getToken());
            String vToken = userRepository.getUserVToken(userId);
            userToken.setValidateToken(vToken == null ? "" : vToken);
            if (StringUtils.isNotEmpty(userTokenFromRedis.getDevice()))
                userToken.setDevice(userTokenFromRedis.getDevice());
            if (StringUtils.isNotEmpty(userTokenFromRedis.getRequestSource()))
                userToken.setRequestSource(userTokenFromRedis.getRequestSource());
            return userToken;
        }
//        LogContext.instance().info("从DB读取TOKEN对象");
        long expiredTime = DateUtil.getCurrentTimeSeconds() - UserConstants.USER_TOKEN_EXPIRE_SECONDS;
        UserToken userTokenFromDB = userRepository.getUserToken(userId, expiredTime);
//        LogContext.instance().info("从DB读取的TOKEN对象是否为空:" + (userTokenFromDB == null));
        return userTokenFromDB;
    }

    private boolean checkTokenOnlyByUserId(SafeInfo safeInfo, String tokenRequestSource) {
//        LogContext.instance().info("获取TOKEN配置");
        String vRequestSources = safeInfo.getValidTokenOnlyUserIdRs();
        if (StringUtils.isEmpty(vRequestSources)) {
            return false;
        }
        String[] vRequestSourceArray = vRequestSources.split(",");
        if (vRequestSourceArray.length == 0) {
            return false;
        }
        for (String str : vRequestSourceArray) {
            if (str.equals(tokenRequestSource)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 登陆错误清零
     */
    public void clearLoginError(int userId) {
        userRepository.clearLoginError(userId);
    }

    /**
     * 修改密码
     */
    public int updatePassword(int userId, String encodedPassword, String salt) {
        return userRepository.updatePassword(userId, encodedPassword, salt);
    }
}
