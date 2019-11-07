package com.bluelion.usercenter.api;

import com.bluelion.shared.helper.BaseService;
import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.model.SafeInfo;
import com.bluelion.usercenter.service.UserBaseService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class UserCenterBaseService extends BaseService {

    @Autowired
    protected UserBaseService userBaseService;

    @Override
    public boolean validateRequestToken(int userId, ApiRequestBody apiRequestBody) throws Exception {
        boolean result = true;
        SafeInfo safeInfo = apiRequestBody.getSafeInfo();
        result = userBaseService.checkUserToken4Client(userId, apiRequestBody.getToken(),
                getDeviceNo4Client(apiRequestBody), safeInfo);
        return result;
    }
}
