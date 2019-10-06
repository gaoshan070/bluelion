package com.bluelion.usercenter.entity;


import lombok.Data;

import java.util.Date;

@Data
public class User {

    private int id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Date registerdate;
}
