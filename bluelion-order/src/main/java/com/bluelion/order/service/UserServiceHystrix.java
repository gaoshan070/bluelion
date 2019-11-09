package com.bluelion.order.service;

import com.bluelion.shared.model.FeignRequest;
import com.bluelion.shared.model.User;
import com.bluelion.shared.utils.ServiceResultUtil;
import org.springframework.stereotype.Service;

@Service
public class UserServiceHystrix implements UserService {


    @Override
    public User userDetail(Integer userId) {
        return null;
    }

    @Override
    public User userDetail() {
        return null;
    }

    @Override
    public String getDetailByEmail(String email) {
        return ServiceResultUtil.serverError("user service failed").toString();
    }

    @Override
    public boolean checkToken(FeignRequest feignRequest) {
        return false;
    }
}
