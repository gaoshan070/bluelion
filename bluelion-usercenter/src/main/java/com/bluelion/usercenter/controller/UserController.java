package com.bluelion.usercenter.controller;

import com.bluelion.shared.utils.JsonUtil;
import com.bluelion.usercenter.entity.User;
import com.bluelion.usercenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//        return userRepository.detail(account);
        User user = new User();
        user.setId(1);
        user.setUsername("test");
        user.setNickname("hill");
        user.setPassword("123456");
        user.setEmail("gaoshan070@gmail.com");
        user.setRegisterdate(new Date());
        return JsonUtil.bean2JsonStr(user);
    }
}
