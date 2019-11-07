package com.bluelion.usercenter.api;

import com.bluelion.shared.helper.IMethodService;
import com.bluelion.shared.model.*;
import com.bluelion.shared.utils.JsonUtil;
import com.bluelion.shared.utils.ServiceResultUtil;
import com.bluelion.usercenter.request.GetUserInfoRequest;
import com.bluelion.usercenter.response.GetUserInfoResponse;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GetUserInfoService extends UserCenterBaseService implements IMethodService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ApiRequestBody getRequestBody(BaseRequest baseRequest) throws Exception {
        GetUserInfoRequest getUserInfoRequest = new GetUserInfoRequest();
        getUserInfoRequest.parseRequest(baseRequest);
        return getUserInfoRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(ApiRequestBody apiRequestBody) {
        GetUserInfoRequest getUserInfoRequest = (GetUserInfoRequest) apiRequestBody;
        return userBaseService.getDetailByEmail(getUserInfoRequest.getAccount());
    }

    @Override
    public String getMethodName() {
        return "get_user_info";
    }

    @Override
    public Result execute4Server() {
        return null;
    }

    @Override
    public Result execute4Client(ApiRequestBody apiRequestBody, User user) throws Exception {
        GetUserInfoRequest getUserInfoRequest = (GetUserInfoRequest) apiRequestBody;
        return dealUserInfo(getUserInfoRequest, user);
    }

    private Result dealUserInfo(GetUserInfoRequest getUserInfoRequest, User user) {
        if (StringUtils.isEmpty(getUserInfoRequest.getAccount())) {
            return ServiceResultUtil.illegal("Invalid params");
        }
        GetUserInfoResponse getUserInfoResponse = new GetUserInfoResponse();
        logger.info("获取用户TOKEN对象");
        UserToken userToken = userBaseService.getUserTokenObject(user.getId());
        if (userToken != null) {
            user.setToken(userToken.getToken());
        }
        getUserInfoResponse.parseFromUser(user);
        JsonElement result = JsonUtil.bean2JsonTree(getUserInfoResponse);
        logger.info("获取用户信息成功");
        return ServiceResultUtil.success(result);
    }
}
