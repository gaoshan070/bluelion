package com.bluelion.usercenter.service;

import com.bluelion.shared.helper.BaseService;
import com.bluelion.shared.helper.IMethodService;
import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.model.BaseRequest;
import com.bluelion.shared.model.Result;
import com.bluelion.shared.utils.ServiceResultUtil;
import com.bluelion.usercenter.request.LoginRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService extends BaseService implements IMethodService {

    @Override
    public String getMethodName() {
        return "login";
    }

    @Override
    public Result execute4Server() {
        return null;
    }

    @Override
    public Result execute4Client(ApiRequestBody apiRequestBody) {
        Map<String, String> result = new HashMap<>();
        LoginRequest loginRequest = (LoginRequest) apiRequestBody;
        result.put("name", loginRequest.getAccount());
        result.put("password", loginRequest.getPassword());
        return ServiceResultUtil.success(result);
    }

    @Override
    public ApiRequestBody getRequestBody(BaseRequest baseRequest) throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        return loginRequest.parseRequest(baseRequest);
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public boolean validateRequestToken(int userId, BaseRequest baseRequest) {
        return false;
    }
}
