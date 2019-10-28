package com.bluelion.shared.helper;

import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.model.BaseRequest;

public abstract class BaseService {

    public abstract ApiRequestBody getRequestBody(BaseRequest baseRequest) throws Exception;

    public abstract boolean checkUser();

    public abstract boolean validateRequestToken(int userId, BaseRequest baseRequest);
}
