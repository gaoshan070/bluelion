package com.bluelion.usercenter.controller;

import com.bluelion.shared.helper.BaseController;
import com.bluelion.shared.model.BaseRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController extends BaseController {

    @GetMapping("/health")
    public String health(){
        return "health";
    }

    @RequestMapping("/greeting")
    public String greeting(){
        return "Welcome to Spring cloud";
    }

    @RequestMapping(value = "/{method}", method = RequestMethod.POST)
    @ResponseBody
    public String dispatch(@PathVariable("method") String method, @RequestBody BaseRequest baseRequest) throws Exception {
        return baseDispatch(method, baseRequest);
    }
}
