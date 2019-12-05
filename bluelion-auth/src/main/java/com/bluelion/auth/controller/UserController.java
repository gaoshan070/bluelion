package com.bluelion.auth.controller;

import com.bluelion.shared.utils.JsonUtil;
import com.google.common.collect.Maps;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@EnableOAuth2Sso
@RequestMapping("/users")
public class UserController {

    @GetMapping(value = "/current")
    public Principal getUser(Principal principal) {
        return principal;
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public String getUser(@PathVariable("id") Integer userId) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("id", userId);
        result.put("name", "shawn");
        return JsonUtil.bean2JsonStr(result);
    }
}
