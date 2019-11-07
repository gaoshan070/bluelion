package com.bluelion.usercenter.controller;

import com.bluelion.shared.model.*;
import com.bluelion.usercenter.service.UserBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserBaseService userBaseService;

    @RequestMapping("/detail/{userId}")
    public User detail(@PathVariable("userId") Integer userId){
        User user = userBaseService.getDetailById(userId);
        return user;
    }

//    @RequestMapping("/detail")
//    public User detail(){
//        User user = userBaseService.getDetailById(1);
//        return user;
//    }
//
//    @RequestMapping("/detail/{email}")
//    public User detailByEmail(@PathVariable("email") String email){
//        User user = userBaseService.getDetailByEmail(email);
//        return user;
//    }

    @PostMapping("/checkUserToken")
    public boolean checkUserToken(@RequestBody FeignRequest feignRequest) throws Exception {
        BaseRequest apiRequestBody = feignRequest.getBaseRequest();
        String inputToken = apiRequestBody.getToken();
        String inputDevice = getDeviceNo4Client(apiRequestBody);
        return userBaseService.checkUserToken4Client(feignRequest.getUserId(), inputToken, inputDevice, feignRequest.getSafeInfo());
    }

    /**
     * 获取设备信息(IOS为IDFA,ANDROID为ANDROID ID)
     */
    public String getDeviceNo4Client(BaseRequest request) throws Exception {
        if (Os.ANDROID.getIndex() == request.getDeviceInfo().getOs()) {
            return request.getDeviceInfo().getAndroidInfo().getAndroidId();
        } else if (Os.IOS.getIndex() == request.getDeviceInfo().getOs()) {
            return request.getDeviceInfo().getIosInfo().getIdfa();
        } else {
            throw new Exception("非法客户端OS");
        }
    }
}
