package com.bluelion.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient(value = "bluelion-usercenter")
public interface UserService {

    @GetMapping("/user/detail")
    String userDetail();
}
