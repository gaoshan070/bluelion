package com.bluelion.shared.helper;

import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.model.BaseRequest;
import com.bluelion.shared.model.Result;
import com.bluelion.shared.utils.JsonUtil;
import com.bluelion.shared.utils.ServiceResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Flux;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//        if(baseService.checkUser()) {
//            //检查用户合法状态
//
//        }
        Result result = methodService.execute4Client(apiRequestBody);
        return result.convert2Result();
    }

}
