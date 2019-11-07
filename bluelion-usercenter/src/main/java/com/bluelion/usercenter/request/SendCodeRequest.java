package com.bluelion.usercenter.request;

import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.model.BaseRequest;
import com.bluelion.shared.utils.JsonUtil;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class SendCodeRequest extends ApiRequestBody {

    /**
     * 邮箱
     */
    @SerializedName("account")
    private String account;
    /**
     * 发送类型:1:忘记密码, 2:修改密码
     */
    @SerializedName("type")
    private int type;

    @Override
    protected ApiRequestBody setFields(String requestString) {
        SendCodeRequest request = JsonUtil.jsonStr2Bean(requestString, SendCodeRequest.class);
        this.account = request.getAccount();
        this.type = request.getType();
        return request;
    }
}
