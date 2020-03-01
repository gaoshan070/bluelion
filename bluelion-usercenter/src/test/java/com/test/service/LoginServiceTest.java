package com.test.service;

import com.bluelion.UserCenterApplication;
import com.bluelion.shared.model.Result;
import com.bluelion.shared.utils.JsonUtil;
import com.bluelion.usercenter.api.LoginService;
import com.bluelion.usercenter.repository.LoginRepository;
import com.bluelion.usercenter.request.LoginRequest;
import com.bluelion.usercenter.service.LoginBaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SpringJUnit4ClassRunner, a JUnit class runner that loads a Spring application context
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserCenterApplication.class)
public class LoginServiceTest {

    @InjectMocks
    LoginBaseService loginBaseService;

    @Mock
    LoginRepository loginRepository;

    @Test
    public void lastLoginInfoTest(){
        loginBaseService.setLastLoginInfo(1, "127.0.0.1");
        loginRepository.setLastLoginInfo(1, "127.0.0.1");

    }
//    @Test
//    public void testDealLogin() throws Exception {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setAccount("gaoshan070@gmail.com");
//        Result result = loginService.execute4Client(loginRequest,null);
//        System.out.println(JsonUtil.bean2JsonStr(result));
//    }
}
