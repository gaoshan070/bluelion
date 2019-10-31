package com.bluelion.usercenter.entity;


import com.bluelion.shared.utils.CodecUtils;
import com.bluelion.shared.utils.ValidateUtil;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class User {

    private int id;
    private String userName;
    private String password;
    private String nickName;
    private String email;
    private String phone;
    private int status;
    private String salt;
    /**
     * 用户token
     */
    private String token;

    /**
     * CP用的登录验证token
     */
    private String vToken;

    /**
     * 获取加密变换后的密码
     */
    public static String getEncodedPassword(String password, String salt) {
        return DigestUtils.md5Hex(DigestUtils.md5Hex(password) + salt);
    }

    /**
     * 生成密码盐
     */
    public static String generateSalt() {
        return CodecUtils.generateRandCode();
    }

    public static boolean isValidLoginName(String loginName) {
        if (StringUtils.isEmpty(loginName))
            return false;
        if (ValidateUtil.isInteger(loginName))
            return false;
        Pattern p = Pattern.compile("[A-Za-z0-9]{4,20}");
        Matcher m = p.matcher(loginName);
        return m.matches();
    }

    public static boolean isValidPassword(String password) {
        if (StringUtils.isEmpty(password))
            return false;
        Pattern p = Pattern.compile("[A-Za-z0-9]{6,20}");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public String generateUserToken() {
        String seed = this.id + "" + CodecUtils.getRequestUUID();
        return DigestUtils.md5Hex(seed);
    }

    public String generateUserValidateToken() {
        String seed = this.id + "" + CodecUtils.getRequestUUID();
        return DigestUtils.sha1Hex(seed);
    }
}
