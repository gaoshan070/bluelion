package com.bluelion.gateway.service;

import com.bluelion.shared.helper.IMethodService;
import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.model.Result;
import com.bluelion.shared.model.User;
import org.springframework.stereotype.Service;

@Service
public class GatewayService implements IMethodService {

    @Override
    public String getMethodName() {
        return "GatewayService";
    }

    @Override
    public Result execute4Server() {
        return null;
    }

    @Override
    public Result execute4Client(ApiRequestBody apiRequestBody, User user) {
        return null;
    }
}
