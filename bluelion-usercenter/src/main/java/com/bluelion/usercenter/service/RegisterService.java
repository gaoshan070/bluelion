package com.bluelion.usercenter.service;

import com.bluelion.shared.helper.BaseService;
import com.bluelion.shared.helper.IMethodService;
import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.model.BaseRequest;
import com.bluelion.shared.model.Result;
import com.bluelion.shared.utils.ServiceResultUtil;
import com.bluelion.usercenter.entity.User;
import com.bluelion.usercenter.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class RegisterService extends BaseService implements IMethodService {

    @Autowired
    private UserBaseService userBaseService;

    @Override
    public ApiRequestBody getRequestBody(BaseRequest baseRequest) throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        return registerRequest.parseRequest(baseRequest);
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public boolean validateRequestToken(int userId, BaseRequest baseRequest) {
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

    @Override
    public Result execute4Client(ApiRequestBody apiRequestBody) {
        RegisterRequest registerRequest = (RegisterRequest) apiRequestBody;
        Map<String, String> result = new HashMap<>();
        result.put("name", "gaoshan");
        result.put("email", "gaoshan070@gmail.com");
        return ServiceResultUtil.success(result);
    }

    private Result dealRegister(RegisterRequest registerRequest){
        if (!User.isValidLoginName(registerRequest.getAccount())) {
            return ServiceResultUtil.illegal("用户名不合法");
        }
        if (!User.isValidPassword(registerRequest.getPassword())) {
            return ServiceResultUtil.illegal("密码不合法");
        }
        String salt = User.generateSalt();
        String encodePwd = User.getEncodedPassword(registerRequest.getPassword(), salt);
        User user = new User();
        user.setEmail(registerRequest.getAccount());
        user.setPassword(encodePwd);
        user.setSalt(salt);
        boolean createUserSuccess = userBaseService.createUser(user);
        if(createUserSuccess) {

            return ServiceResultUtil.success();
        } else {
            return ServiceResultUtil.illegal("Fail to register");
        }

    }
}
