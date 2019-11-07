package com.bluelion.order.api;

import com.bluelion.order.service.UserService;
import com.bluelion.shared.helper.BaseService;
import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.model.FeignRequest;
import com.bluelion.shared.model.SafeInfo;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class OrderBaseService extends BaseService {

    @Autowired
    protected UserService userService;

    @Override
    public boolean validateRequestToken(int userId, ApiRequestBody baseRequest) throws Exception {
        boolean result = true;
        SafeInfo safeInfo = baseRequest.getSafeInfo();
        FeignRequest feignRequest = new FeignRequest();
        feignRequest.setSafeInfo(safeInfo);
        feignRequest.setBaseRequest(baseRequest);
        feignRequest.setUserId(userId);
        result = userService.checkToken(feignRequest);
        return result;
    }
}
