package com.bluelion.usercenter.request;

import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.utils.JsonUtil;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class GetUserInfoRequest extends ApiRequestBody {

    @SerializedName("account")
    private String account;

    @Override
    protected ApiRequestBody setFields(String requestString) {
        GetUserInfoRequest request = JsonUtil.jsonStr2Bean(requestString, GetUserInfoRequest.class);
        this.account = request.getAccount();
        return request;
    }
}
