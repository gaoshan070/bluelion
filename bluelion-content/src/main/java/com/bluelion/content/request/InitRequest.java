package com.bluelion.content.request;

import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.utils.JsonUtil;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class InitRequest extends ApiRequestBody {

    @SerializedName("is_test")
    private int isTest = 0;

    @Override
    protected ApiRequestBody setFields(String requestString) {
        InitRequest request = JsonUtil.jsonStr2Bean(requestString, InitRequest.class);
        this.isTest = request.getIsTest();
        return request;
    }
}
