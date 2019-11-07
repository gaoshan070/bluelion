package com.bluelion.content.request;

import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.utils.JsonUtil;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class QaListRequest extends ApiRequestBody {

    @SerializedName("user_id")
    private Integer userId;

    @Override
    protected ApiRequestBody setFields(String requestString) {
        QaListRequest request = JsonUtil.jsonStr2Bean(requestString, QaListRequest.class);
        this.userId = request.getUserId();
        return request;
    }
}
