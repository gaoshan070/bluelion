package com.bluelion.content.service;

import com.bluelion.shared.model.FeignRequest;
import com.bluelion.shared.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(value = "bluelion-usercenter")
public interface UserService {

    @GetMapping("/user/detail/{userId}")
    User userDetail(@PathVariable(value = "userId") Integer userId);

    @PostMapping("/user/checkUserToken")
    boolean checkToken(@RequestBody FeignRequest feignRequest);
}
