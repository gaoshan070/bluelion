package com.bluelion.usercenter.request;

import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.utils.JsonUtil;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class RegisterRequest extends ApiRequestBody {

    @SerializedName("account")
    private String account;

    @SerializedName("password")
    private String password;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("phone")
    private String phone;

    @Override
    protected ApiRequestBody setFields(String requestString) {
        RegisterRequest request = JsonUtil.jsonStr2Bean(requestString, RegisterRequest.class);
        this.account = request.getAccount();
        this.password = request.getPassword();
        this.fullName = request.getFullName();
        this.phone = request.getPhone();
        return request;
    }
}
