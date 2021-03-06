package com.bluelion.shared.constants;

public class UserConstants {

    private UserConstants() {
    }

    /**
     * TOKEN过期时间7天
     */
    public static final int USER_TOKEN_EXPIRE_SECONDS = 604800;

    /**
     * CP TOKEN过期时间 1分钟
     */
    public static final int USER_VALIDATE_TOKEN_EXPIRE_SECONDS = 60;
    /**
     * 验证码过期时间（秒）
     */
    public static final int VALIDATION_CODE_EXPIRE_SECONDS = 1800;
    /**
     * 两次验证码发送时间间隔（秒）
     */
    public static final int VALIDATION_CODE_SEND_INTERVAL_SECONDS = 60;

    /**
     * 一天内同设备发送短信或者邮件的限制次数
     */
    public static final int SEND_SMS_OR_EMAIL_COUNT_EVERY_DAY = 15;
    /**
     * 一天内同设备登录错误限制次数
     */
    public static final int DEVICE_LOGIN_ERROR_COUNT_EVERY_DAY = 10;
    /**
     * 设备登录黑名单未启用状态
     */
    public static final int DEVICE_LOGIN_BLACK_LIST_NO_STATUS = 0;
    /**
     * 设备登录黑名单启用状态
     */
    public static final int DEVICE_LOGIN_BLACK_LIST_YES_STATUS = 1;

    /**
     * 发送验证码类型-短信
     */
    public static final int SEND_CODE_SMS_TYPE = 1;
    /**
     * 发送验证码类型-邮箱
     */
    public static final int SEND_CODE_EMAIL_TYPE = 2;

    /**
     * 短信发送验证码模板
     */
    public static final String SEND_CODE_TEMPLATE_4_SMS = " is your Bluelion verification code which will be expired in 30 mins.";
    /**
     * 邮件发送验证码模板
     */
    public static final String SEND_CODE_TEMPLATE_4_EMAIL = SEND_CODE_TEMPLATE_4_SMS + "【BlueLion】";
}
