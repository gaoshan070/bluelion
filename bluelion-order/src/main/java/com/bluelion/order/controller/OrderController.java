package com.bluelion.order.controller;

import com.bluelion.order.service.ContentService;
import com.bluelion.order.service.UserService;
import com.bluelion.shared.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    UserService userService;

    @Autowired
    ContentService contentService;

    @GetMapping("/user")
    public User getUserDetail() {
        return userService.userDetail(1);
    }

    @GetMapping("/order/info/{id}")
    public String getOrderDetail(@PathVariable String id) {
        return contentService.orderInfo(id);
    }
}
