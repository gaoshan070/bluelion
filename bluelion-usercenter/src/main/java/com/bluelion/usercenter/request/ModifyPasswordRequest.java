package com.bluelion.usercenter.request;

import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.utils.JsonUtil;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ModifyPasswordRequest extends ApiRequestBody {

    /**
     * 用户账号:用户名 邮箱
     */
    @SerializedName("account")
    private String account;
    /**
     * 旧密码
     */
    @SerializedName("old_password")
    private String oldPassword;
    /**
     * 新密码
     */
    @SerializedName("new_password")
    private String newPassword;
    /**
     * 验证码
     */
    @SerializedName("validation_code")
    private String validationCode;


    @Override
    protected ApiRequestBody setFields(String requestString) {
        ModifyPasswordRequest request = JsonUtil.jsonStr2Bean(requestString, ModifyPasswordRequest.class);
        this.account = request.getAccount();
        this.oldPassword = request.getOldPassword();
        this.newPassword = request.getNewPassword();
        this.validationCode = request.getValidationCode();
        return request;
    }
}
