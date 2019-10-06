package com.bluelion.shared.enums;

public enum ResultCodeEnum {
    SUCCESS("1000", "Success"),
    PARAMS_INVALID("1001", "Params are invalid"),
    SERVICE_NOT_FOUND("1004","The service is not found"),
    UNSUPPORTED_METHOD("1005","Unsupported Operation"),
    SGIN_EMPTY("6001", "The sign is empty"),
    PUBLICKEY_EMPTY("6002", "The public key is empty"),
    PRIVATEKEY_EXPIRE("6003", "The private key is expired"),
    SIGN_INVALID("6004", "The sign is invalid"),
    TIMEOUT("6005", "Server timeout");

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
