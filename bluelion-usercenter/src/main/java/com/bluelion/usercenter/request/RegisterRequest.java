package com.bluelion.usercenter.request;

import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.model.BaseRequest;
import com.bluelion.shared.utils.JsonUtil;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class RegisterRequest extends ApiRequestBody {

    @SerializedName("account")
    private String account;

    @SerializedName("password")
    private String password;

    @SerializedName("confirm_password")
    private String confirmPassword;

    @Override
    protected ApiRequestBody setFields(String requestString) {
        RegisterRequest request = JsonUtil.jsonStr2Bean(requestString, RegisterRequest.class);
        this.account = request.getAccount();
        this.password = request.getPassword();
        this.confirmPassword = request.getConfirmPassword();
        return request;
    }
}
