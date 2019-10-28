package com.bluelion.shared.helper;

import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.model.Result;

public interface IMethodService {

    String getMethodName();

    Result execute4Server();

    Result execute4Client(ApiRequestBody apiRequestBody);
}
