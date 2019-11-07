package com.bluelion.order.request;

import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.utils.JsonUtil;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class PrintHistoryRequest extends ApiRequestBody {

    @SerializedName("user_id")
    private Integer userId;

    @Override
    protected ApiRequestBody setFields(String requestString) {
        PrintHistoryRequest request = JsonUtil.jsonStr2Bean(requestString, PrintHistoryRequest.class);
        this.userId = request.getUserId();
        return request;
    }
}
