package com.bluelion.shared.helper;

import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.model.Result;
import com.bluelion.shared.model.User;

public interface IMethodService {

    String getMethodName();

    Result execute4Server();

    Result execute4Client(ApiRequestBody apiRequestBody, User user) throws Exception;
}
