package com.bluelion.usercenter.service;

import com.bluelion.shared.helper.BaseService;
import com.bluelion.shared.helper.IMethodService;
import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.model.BaseRequest;
import com.bluelion.shared.model.Result;
import com.bluelion.shared.utils.ServiceResultUtil;
import com.bluelion.usercenter.request.RegisterRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterService extends BaseService implements IMethodService {

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

}
