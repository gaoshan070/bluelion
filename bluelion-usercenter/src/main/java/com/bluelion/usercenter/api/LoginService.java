package com.bluelion.usercenter.api;

import com.bluelion.shared.helper.BaseService;
import com.bluelion.shared.helper.IMethodService;
import com.bluelion.shared.model.*;
import com.bluelion.shared.utils.JsonUtil;
import com.bluelion.shared.utils.ServiceResultUtil;
import com.bluelion.usercenter.request.LoginRequest;
import com.bluelion.usercenter.response.LoginResponse;
import com.bluelion.usercenter.service.LoginBaseService;
import com.bluelion.usercenter.service.UserBaseService;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService extends UserCenterBaseService implements IMethodService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoginBaseService loginBaseService;

    @Override
    public String getMethodName() {
        return "login";
    }

    @Override
    public Result execute4Server() {
        return null;
    }

    @Override
    public Result execute4Client(ApiRequestBody apiRequestBody, User user) throws Exception {
        LoginRequest loginRequest = (LoginRequest) apiRequestBody;
        return dealLogin(loginRequest);
    }

    @Override
    public ApiRequestBody getRequestBody(BaseRequest baseRequest) throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.parseRequest(baseRequest);
        return loginRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(ApiRequestBody baseRequest) {
        return null;
    }

    private Result dealLogin(LoginRequest loginRequest) throws Exception {
        if (StringUtils.isEmpty(loginRequest.getAccount())) {
            return ServiceResultUtil.illegal("This account is not existed");
        }
        User user = userBaseService.getDetailByEmail(loginRequest.getAccount());
        String errorMessage = getUserStatusErrorMessage(user);
        if(StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }

        boolean isClientRequest = isClientRequest(loginRequest);
        boolean isAutoLogin = false;
        if(isClientRequest && StringUtils.isEmpty(loginRequest.getPassword())
                && StringUtils.isNotEmpty(loginRequest.getToken())) {
            isAutoLogin = true;
            //TODO 自动登录
            String tokenDevice = getDeviceNo4Client(loginRequest);
            boolean checkResult = userBaseService.checkUserToken4Client(user.getId(),
                            loginRequest.getToken(), tokenDevice, loginRequest.getSafeInfo());
            if (!checkResult) {
                return ServiceResultUtil.illegal("Token is expired, please sign in again");
            }
        }

        if(!isAutoLogin && !user.isPasswordValid(loginRequest.getPassword())) {
            //TODO 验证设备登录

            return ServiceResultUtil.illegal(dealErrorPwd(user));
        }
        String tokenDevice = "";
        LoginResponse loginResponse = new LoginResponse();
        if(isClientRequest) {
//            Os os = getClientOsByRequest(loginRequest);
            tokenDevice = getDeviceNo4Client(loginRequest);
        }
        loginBaseService.setLastLoginInfo(user.getId(), loginRequest.getIp());
        boolean updateTokenResult = userBaseService.updateToken(user, tokenDevice, loginRequest.getSafeInfo(), false);
        logger.info("更新token结果："+ updateTokenResult);
        loginResponse.parseFromUser(user);
        JsonElement result = JsonUtil.bean2JsonTree(loginResponse);
        logger.info("登录成功");
        return ServiceResultUtil.success(result);
    }

    private String dealErrorPwd(User user) throws Exception {
        int errorCount;
        if (user.isLoginErrorInOneHour()) {
            if (user.isTouchLoginErrorTimes()) {
                throw new Exception("逻辑错误，已验证过该路径");
            } else {
                errorCount = user.getLoginError();
            }
        } else {
            loginBaseService.clearLoginError(user.getId());
            errorCount = 0;
        }
        loginBaseService.setLoginError(user.getId(), errorCount + 1);
        if (errorCount > 2 && errorCount < 5) {
            return "Wrong password. If input wrong password 5 times, this account will be banned for 1 hour，"  + (5 - errorCount) + "times";
        }
        return "Wrong password";
    }
}
