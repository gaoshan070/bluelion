package com.bluelion.order.controller;

import com.bluelion.shared.helper.BaseController;
import com.bluelion.shared.model.BaseRequest;
import com.bluelion.shared.utils.JsonUtil;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController extends BaseController {

    @GetMapping("/health")
    public String health() {
        return "welcome to order server";
    }

    @RequestMapping(value = "/{method}", method = RequestMethod.POST)
    @ResponseBody
    public String dispatch(@PathVariable("method") String method, @RequestBody String requestBody) throws Exception {
        BaseRequest baseRequest = JsonUtil.jsonStr2Bean(requestBody, BaseRequest.class);
        return baseDispatch(method, baseRequest, CommonLoggerNameConstants.REQUEST_PARAM_LOG_NAME);
    }
}
