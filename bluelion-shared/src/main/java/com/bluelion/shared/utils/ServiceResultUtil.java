package com.bluelion.shared.utils;

import com.bluelion.shared.enums.ResultCodeEnum;
import com.bluelion.shared.model.Result;
import com.google.gson.JsonElement;

public class ServiceResultUtil {

    private ServiceResultUtil() {

    }

    /**
     * 构造token失效结果
     */
    public static Result tokenExpired() {
        return new Result(ResultCodeEnum.TOKEN_EXPIRED.getCode(), "令牌检查失败");
    }

    /**
     * 构造非法请求结果
     */
    public static Result illegal() {
        return new Result(ResultCodeEnum.ILLEGAL_REQUEST.getCode(), ResultCodeEnum.ILLEGAL_REQUEST.getMsg());
    }

    /**
     * 构造非法请求结果
     */
    public static Result illegal(String msg) {
        return new Result(ResultCodeEnum.ILLEGAL_REQUEST.getCode(), msg);
    }

    /**
     * 不支持的请求方式
     */
    public static Result unsupportedMethod() {
        return new Result(ResultCodeEnum.UNSUPPORTED_METHOD.getCode(), ResultCodeEnum.UNSUPPORTED_METHOD.getMsg());
    }

    public static Result notFound() {
        return new Result(ResultCodeEnum.SERVICE_NOT_FOUND.getCode(), ResultCodeEnum.SERVICE_NOT_FOUND.getMsg());
    }
    /**
     * 构造成功请求结果
     */
    public static Result success(JsonElement result) {
        return new Result(result);
    }

    /**
     * 构造成功请求结果
     */
    public static Result success(Object responseBean) {
        return success(JsonUtil.bean2JsonTree(responseBean));
    }

    /**
     * 构造成功请求结果
     */
    public static Result success() {
        return new Result();
    }

    /**
     * 构造服务器异常结果
     */
    public static Result serverError(String msg) {
        return new Result(ResultCodeEnum.SERVER_ERROR.getCode(), msg);
    }


}
