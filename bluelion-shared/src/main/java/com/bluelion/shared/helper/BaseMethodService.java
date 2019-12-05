package com.bluelion.shared.helper;

import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.model.Result;
import com.bluelion.shared.model.User;
import org.springframework.stereotype.Service;

@Service
public class BaseMethodService implements IMethodService {

    @Override
    public String getMethodName() {
        return null;
    }

    @Override
    public Result execute4Server() {
        return null;
    }

    @Override
    public Result execute4Client(ApiRequestBody apiRequestBody, User user) throws Exception {
        return null;
    }
}
