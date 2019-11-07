package com.bluelion.usercenter.api;

import com.bluelion.shared.helper.IMethodService;
import com.bluelion.shared.model.*;
import com.bluelion.shared.utils.ServiceResultUtil;
import com.bluelion.usercenter.request.LogoutRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogoutService extends UserCenterBaseService implements IMethodService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ApiRequestBody getRequestBody(BaseRequest baseRequest) throws Exception {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.parseRequest(baseRequest);
        return logoutRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(ApiRequestBody baseRequest) {
        LogoutRequest logoutRequest = (LogoutRequest) baseRequest;
        return userBaseService.getDetailByEmail(logoutRequest.getAccount());
    }

    @Override
    public String getMethodName() {
        return "logout";
    }

    @Override
    public Result execute4Server() {
        return null;
    }

    @Override
    public Result execute4Client(ApiRequestBody apiRequestBody, User user) throws Exception {
        LogoutRequest logoutRequest = (LogoutRequest) apiRequestBody;
        return dealLogout(logoutRequest, user);
    }

    private Result dealLogout(LogoutRequest logoutRequest, User user) {
        if (StringUtils.isEmpty(logoutRequest.getAccount())) {
            return ServiceResultUtil.illegal("Invalid request params");
        }
        boolean result = userBaseService.deleteToken(user.getId());
        if (result) {
            logger.info("注销成功");
            return ServiceResultUtil.success();
        } else {
            return ServiceResultUtil.illegal("Fail to logout");
        }
    }
}
