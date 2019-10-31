package com.bluelion.usercenter.service;

import com.bluelion.shared.constants.UserConstants;
import com.bluelion.shared.utils.DateUtil;
import com.bluelion.usercenter.cache.IUserCacheManager;
import com.bluelion.usercenter.entity.User;
import com.bluelion.usercenter.entity.UserToken;
import com.bluelion.usercenter.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBaseService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    IUserCacheManager userCacheManager;

    public boolean createUser(User user) {
        userRepository.addUser(user);
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
     * 更新token信息
     */
//    public boolean updateToken(User user, String device, boolean isRegister) {
////        LogContext.instance().info("更新TOKEN逻辑");
//        if (user == null)
//            return false;
//        if (!isRegister) {
////            LogContext.instance().info("不是注册请求,进入检查是否是同设备和TOKEN不顶号配置检查");
//            UserToken userToken = getUserTokenObject(user.getId());
//            boolean checkTokenOnlyByUserId = false;
//            boolean isNoCheckTokenDevice = false;
//            if (userToken != null) {
//                checkTokenOnlyByUserId = checkTokenOnlyByUserId(safeInfo, userToken.getRequestSource());
//                isNoCheckTokenDevice = StringUtils.isNotEmpty(device) && device.equals(userToken.getDevice());
//            }
//            if (userToken != null && (isNoCheckTokenDevice || checkTokenOnlyByUserId)) {
////                LogContext.instance().info("不更新TOKEN,更新VTOKEN");
//                String vToken = user.generateUserValidateToken();
//                boolean updateSuccess = userRepository.updateUserValidateToken(user.getId(), vToken);
//                boolean updateCacheSuccess = false;
//                if (updateSuccess) {
//                    updateCacheSuccess = userCacheManager.setUserValidateToken(user.getId(), vToken);
//                    user.setVToken(vToken);
//                }
////                LogContext.instance().info("更新VTOKEN缓存结果:" + updateCacheSuccess);
//                user.setToken(userToken.getToken());
//                return true;
//            }
//        }
//        String token = user.generateUserToken();
//        String validateToken = user.generateUserValidateToken();
//        boolean updateDBTokenResult = userRepository.addOrUpdateUserToken(user.getId(), token,
//                validateToken, device, safeInfo.getRequestSource());
////        LogContext.instance().info("更新DB TOKEN");
//        if (!updateDBTokenResult) {
////            LogContext.instance().error("更新DB TOKEN失败");
//            return false;
//        }
//        boolean updateRedisUserTokenResult = userCacheManager.setUserToken(user.getId(), token,
//                device, safeInfo.getRequestSource());
//        boolean updateRedisUserVTokenResult = userCacheManager.setUserValidateToken(user.getId(), validateToken);
//        boolean isSuccess = updateRedisUserTokenResult && updateRedisUserVTokenResult;
//        user.setToken(token);
//        user.setVToken(validateToken);
////        LogContext.instance().info("更新缓存TOKEN");
//        if (!isSuccess) {
////            LogContext.instance().error("更新缓存TOKEN失败");
//            return false;
//        }
//        return true;
//    }

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

//    private boolean checkTokenOnlyByUserId(SafeInfo safeInfo, String tokenRequestSource) {
////        LogContext.instance().info("获取TOKEN配置");
//        String vRequestSources = safeInfo.getValidTokenOnlyUserIdRs();
//        if (StringUtils.isEmpty(vRequestSources)) {
//            return false;
//        }
//        String[] vRequestSourceArray = vRequestSources.split(",");
//        if (vRequestSourceArray.length == 0) {
//            return false;
//        }
//        for (String str : vRequestSourceArray) {
//            if (str.equals(tokenRequestSource)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
