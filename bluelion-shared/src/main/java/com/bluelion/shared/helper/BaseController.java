package com.bluelion.shared.helper;

import com.bluelion.shared.model.*;
import com.bluelion.shared.utils.ServiceResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {

    @Autowired
    MethodServiceFactory methodServiceFactory;

    protected String baseDispatch(String method, BaseRequest baseRequest) throws Exception {
        if(!methodServiceFactory.getMethodServiceMap().containsKey(method)) {
            return ServiceResultUtil.notFound().convert2Result();
        }
        IMethodService methodService = methodServiceFactory.getMethodServiceMap().get(method);
        BaseService baseService = ((BaseService) methodService);
        ApiRequestBody apiRequestBody = baseService.getRequestBody(baseRequest);

        User user = null;
        if (baseService.checkUser()) {
//            LogContext.instance().info("用户状态合法性检查");
            user = baseService.getUser(apiRequestBody);
            String message = baseService.getUserStatusErrorMessage(user);
            if (StringUtils.isNotEmpty(message)) {
//                LogContext.instance().warn("用户状态异常:" + message);
                return ServiceResultUtil.illegal("User status error:" + message).convert2Result();
            }
            if (!baseService.validateRequestToken(user.getId(), apiRequestBody)) {
                return ServiceResultUtil.illegal("Invalid Token").convert2Result();
            }
        }
        Result result = methodService.execute4Client(apiRequestBody, user);
        return result.convert2Result();
    }

    private boolean isClientRequest(SafeInfo safeInfo) {
        return safeInfo.getIsClient() == 1;
    }
}
