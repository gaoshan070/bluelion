package com.bluelion.order.service;

import com.bluelion.shared.model.FeignRequest;
import com.bluelion.shared.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(value = "bluelion-usercenter")
public interface UserService {

    @GetMapping("/user/detail/{userId}")
    User userDetail(@PathVariable(value = "userId") Integer userId);

    @GetMapping("/user/detail")
    User userDetail();

    @GetMapping("/user/detail/{email}")
    String getDetailByEmail(@PathVariable(value = "email") String email);

    @PostMapping("/user/checkUserToken")
    boolean checkToken(@RequestBody FeignRequest feignRequest);
}
