package com.bluelion.usercenter.request;

import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.utils.JsonUtil;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class LoginRequest extends ApiRequestBody {

    @SerializedName("account")
    private String account;

    @SerializedName("password")
    private String password;

    @Override
    protected ApiRequestBody setFields(String requestString) {
        LoginRequest request = JsonUtil.jsonStr2Bean(requestString, LoginRequest.class);
        this.account = request.getAccount();
        this.password = request.getPassword();
        return request;
    }
}
