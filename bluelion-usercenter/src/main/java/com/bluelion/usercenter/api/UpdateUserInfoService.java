package com.bluelion.usercenter.api;

import com.bluelion.shared.helper.IMethodService;
import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.model.BaseRequest;
import com.bluelion.shared.model.Result;
import com.bluelion.shared.model.User;
import com.bluelion.shared.utils.ServiceResultUtil;
import com.bluelion.usercenter.request.UpdateUserInfoRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserInfoService extends UserCenterBaseService implements IMethodService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ApiRequestBody getRequestBody(BaseRequest baseRequest) throws Exception {
        UpdateUserInfoRequest updateUserInfoRequest = new UpdateUserInfoRequest();
        updateUserInfoRequest.parseRequest(baseRequest);
        return updateUserInfoRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(ApiRequestBody apiRequestBody) {
        UpdateUserInfoRequest updateUserInfoRequest = (UpdateUserInfoRequest) apiRequestBody;
        return userBaseService.getDetailByEmail(updateUserInfoRequest.getAccount());
    }

    @Override
    public String getMethodName() {
        return "update_user_info";
    }

    @Override
    public Result execute4Server() {
        return null;
    }

    @Override
    public Result execute4Client(ApiRequestBody apiRequestBody, User user) throws Exception {
        UpdateUserInfoRequest updateUserInfoRequest = (UpdateUserInfoRequest) apiRequestBody;
        return dealUpdateUserInfo(updateUserInfoRequest, user);
    }

    private Result dealUpdateUserInfo(UpdateUserInfoRequest updateUserInfoRequest, User user) {
        if (StringUtils.isEmpty(updateUserInfoRequest.getAccount())) {
            return ServiceResultUtil.illegal("Invalid params");
        }
        user.setUserName(updateUserInfoRequest.getFullName());
        user.setPhone(updateUserInfoRequest.getPhone());
        user.setAddress1(updateUserInfoRequest.getAddress1());
        user.setAddress2(updateUserInfoRequest.getAddress2());
        user.setCity(updateUserInfoRequest.getCity());
        boolean success = userBaseService.updateUser(user);
        if(success) {
            logger.info("修改用户信息成功");
            return ServiceResultUtil.success();
        } else {
            logger.error("修改用户信息失败");
            return ServiceResultUtil.serverError("Fail to update user info");
        }

    }
}
