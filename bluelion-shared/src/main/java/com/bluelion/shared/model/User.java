package com.bluelion.shared.model;


import com.bluelion.shared.constants.UserConstants;
import com.bluelion.shared.constants.UserStatus;
import com.bluelion.shared.utils.CodecUtils;
import com.bluelion.shared.utils.DateUtil;
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
    private String address1;
    private String address2;
    private String city;
    private Integer companyId;
    private String company;
    private String appId;
    /**
     * 登录错误时的时间戳
     */
    private int loginErrorTime;
    /**
     * 登录错误次数
     */
    private int loginError;
    /**
     * 用户token
     */
    private String token;

    /**
     * CP用的登录验证token
     */
    private String vToken;

    public boolean isLoginErrorInOneHour() {
        return System.currentTimeMillis() / 1000 - this.loginErrorTime <= 3600;
    }

    public boolean isTouchLoginErrorTimes() {
        return this.loginError >= 5;
    }
    public boolean isPasswordValid(String password) {
        String encodedPassword = getEncodedPassword(password, this.salt);
        return this.password.equals(encodedPassword);
    }

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

    /**
     * 检查用户状态
     *
     * @return 可用状态返回null，否则返回错误提示语
     */
    public String checkStatus() {
        UserStatus us = getUserStatus();
        if (UserStatus.VALID.equals(us)) {
            return null;
        }
        switch (us) {
            case INVALID:
                return "This account is non-active";
            case PAUSE:
                return "This account is paused";
            case DENY:
                return "This account is banned";
            default:
                return "This account is not existed";
        }
    }

    public UserStatus getUserStatus() {
        return UserStatus.valueOf(status);
    }

    /**
     * 检查验证码是否有效
     */
    public static boolean isValidationCodeValid(String validationCodeInput, String validationCodeReal, long validationCodeTime) {
        return StringUtils.isNotEmpty(validationCodeInput)
                && StringUtils.isNotEmpty(validationCodeReal)
                && validationCodeInput.toLowerCase().equals(validationCodeReal.toLowerCase())
                && DateUtil.getCurrentTimeSeconds() - validationCodeTime < UserConstants.VALIDATION_CODE_EXPIRE_SECONDS;
    }
}
