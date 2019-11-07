package com.bluelion.usercenter.api;

import com.bluelion.shared.helper.BaseService;
import com.bluelion.shared.helper.IMethodService;
import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.model.BaseRequest;
import com.bluelion.shared.model.Result;
import com.bluelion.shared.model.User;
import com.bluelion.shared.utils.JsonUtil;
import com.bluelion.shared.utils.ServiceResultUtil;
import com.bluelion.shared.utils.ValidateUtil;
import com.bluelion.usercenter.request.RegisterRequest;
import com.bluelion.usercenter.response.RegisterResponse;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService extends UserCenterBaseService implements IMethodService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ApiRequestBody getRequestBody(BaseRequest baseRequest) throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.parseRequest(baseRequest);
        return registerRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(ApiRequestBody baseRequest) {
        return null;
    }

    @Override
    public boolean validateRequestToken(int userId, ApiRequestBody apiRequestBody) {
        return false;
    }

    @Override
    public String getMethodName() {
        return "register";
    }

    @Override
    public Result execute4Server() {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result execute4Client(ApiRequestBody apiRequestBody, User user) throws Exception {
        RegisterRequest registerRequest = (RegisterRequest) apiRequestBody;
        return dealRegister(registerRequest);
    }

    private Result dealRegister(RegisterRequest registerRequest) throws Exception {
        if(!ValidateUtil.isEmail(registerRequest.getAccount())) {
            return ServiceResultUtil.illegal("Invalid email");
        }
        if (!User.isValidPassword(registerRequest.getPassword())) {
            return ServiceResultUtil.illegal("Invalid password");
        }
        String salt = User.generateSalt();
        String encodePwd = User.getEncodedPassword(registerRequest.getPassword(), salt);
        User user = new User();
        user.setUserName(registerRequest.getFullName());
        user.setPhone(registerRequest.getPhone());
        user.setEmail(registerRequest.getAccount());
        user.setPassword(encodePwd);
        user.setSalt(salt);

        String appId = isClientRequest(registerRequest) ? registerRequest.getOtherInfo().getAppId()
                : registerRequest.getAppId();
        boolean createUserSuccess = userBaseService.createUser(user, appId, registerRequest.getIp());
        if(createUserSuccess) {
            RegisterResponse registerResponse = new RegisterResponse();
            logger.info("插入用户成功");
            User newUser = userBaseService.getDetailByEmail(registerRequest.getAccount());
            String tokenDevice = "";
            if (isClientRequest(registerRequest)) {
                //TODO 判断是否为新设备
                tokenDevice = getDeviceNo4Client(registerRequest);
            }
            boolean updateTokenResult = userBaseService.updateToken(newUser, tokenDevice, registerRequest.getSafeInfo(), true);
            logger.info("更新用户token:" + updateTokenResult);
            registerResponse.parseFromUser(newUser);
            JsonElement result = JsonUtil.bean2JsonTree(registerResponse);
            logger.info("注册成功");
            return ServiceResultUtil.success(result);
        } else {
            return ServiceResultUtil.illegal("Fail to register");
        }

    }
}
