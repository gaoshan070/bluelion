package com.bluelion.shared.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserToken {

    private String token;
    private String validateToken;
    private String device;
    private String requestSource;
    private Date createTime;
}
