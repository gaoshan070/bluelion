package com.bluelion.usercenter.controller;

import com.bluelion.shared.helper.BaseController;
import com.bluelion.shared.utils.JsonUtil;
import com.bluelion.usercenter.entity.User;
import com.bluelion.usercenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login/{username}/{password}")
    public User login(@PathVariable("username") String username, @PathVariable("password") String password){
        User account =  userRepository.login(username, password);
        return account;
    }

    @RequestMapping("/detail")
    public String detail(){
        User user = new User();
        user.setId(1);
        user.setUserName("test");
        user.setNickName("hill");
        user.setPassword("123456");
        user.setEmail("gaoshan070@gmail.com");
        return JsonUtil.bean2JsonStr(user);
    }
}
