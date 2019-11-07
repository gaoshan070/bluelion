package com.bluelion.shared.model;

import lombok.Data;

/**
 * 验证码
 * Created by liuyang on 16/6/2.
 */
@Data
public class ValidateCode {

    private String mobileOrEmail;
    private String vcode;
    private long vcodeTime;
    private int type;
    private int purpose;

}
