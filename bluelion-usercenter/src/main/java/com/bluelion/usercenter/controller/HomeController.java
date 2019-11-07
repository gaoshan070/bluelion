package com.bluelion.usercenter.controller;

import com.bluelion.shared.helper.BaseController;
import com.bluelion.shared.model.BaseRequest;
import com.bluelion.shared.utils.JsonUtil;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController extends BaseController {

    @GetMapping("/health")
    public String health(){
        return "health";
    }

    @RequestMapping("/greeting")
    public String greeting(@RequestBody Object request){
        System.out.println(JsonUtil.bean2JsonStr(request));
        return "Welcome to Spring cloud";
    }

    @RequestMapping(value = "/{method}", method = RequestMethod.POST)
    @ResponseBody
    public String dispatch(@PathVariable("method") String method, @RequestBody String requestBody) throws Exception {
        BaseRequest baseRequest = JsonUtil.jsonStr2Bean(requestBody, BaseRequest.class);
        return baseDispatch(method, baseRequest);
    }
}
