package com.bluelion;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.bluelion.usercenter.repository")
public class UserCenterApplication {

    public static void main(String[] args){
        SpringApplication.run(UserCenterApplication.class, args);
    }
}
