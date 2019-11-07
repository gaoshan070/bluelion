package com.bluelion.shared.helper;

import com.bluelion.shared.model.*;
import org.apache.commons.lang3.StringUtils;

public abstract class BaseService {

    public abstract ApiRequestBody getRequestBody(BaseRequest baseRequest) throws Exception;

    public abstract boolean checkUser();

    public abstract User getUser(ApiRequestBody apiRequestBody);

    public abstract boolean validateRequestToken(int userId, ApiRequestBody apiRequestBody) throws Exception;

    /**
     * 获取用户状态错误提示语
     */
    public String getUserStatusErrorMessage(User user) {
//        LogContext.instance().info("检查用户状态");
        if (user == null || user.getId() <= 0) {
//            LogContext.instance().warn("用户不存在");
            return "User is not existed";
        }
        String error = user.checkStatus();
        if (StringUtils.isNotEmpty(error)) {
//            LogContext.instance().error(error);
            return error;
        }
//        if (user.isLoginLocked()) {
//            LogContext.instance().error("用户(" + user.getId() + ")被禁用1小时");
//            userBaseService.deleteToken(user.getId());
//            return "此帐号1小时内登录错误超过5次，禁用1小时";
//        }
//        LogContext.instance().info("用户状态正常");
        return "";
    }

    /**
     * 获取设备信息(IOS为IDFA,ANDROID为ANDROID ID)
     */
    public String getDeviceNo4Client(BaseRequest request) throws Exception {
        if (Os.ANDROID.getIndex() == request.getDeviceInfo().getOs()) {
            return request.getDeviceInfo().getAndroidInfo().getAndroidId();
        } else if (Os.IOS.getIndex() == request.getDeviceInfo().getOs()) {
            return request.getDeviceInfo().getIosInfo().getIdfa();
        } else {
            throw new Exception("非法客户端OS");
        }
    }

    /**
     * 是否是客户端请求
     */
    protected boolean isClientRequest(BaseRequest request) throws Exception {
        SafeInfo safeInfo = request.getSafeInfo();
        if (safeInfo == null)
            throw new Exception("安全加固对象为空");
        boolean result = safeInfo.getIsClient() == 1;
//        LogContext.instance().info("是否是客户端请求:" + result);
        return result;
    }

    /**
     * 根据请求对象获取客户端OS对象
     */
    public Os getClientOsByRequest(BaseRequest request) {
        if (request == null || request.getDeviceInfo() == null)
            return null;
        if (request.getDeviceInfo().getOs() != Os.IOS.getIndex()
                && request.getDeviceInfo().getOs() != Os.ANDROID.getIndex())
            return null;
        return Os.getOsByIndex(request.getDeviceInfo().getOs());
    }
}
