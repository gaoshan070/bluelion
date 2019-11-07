package com.bluelion.usercenter.request;

import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.utils.JsonUtil;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UpdateUserInfoRequest extends ApiRequestBody {

    @SerializedName("account")
    private String account;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address1")
    private String address1;

    @SerializedName("address2")
    private String address2;

    @SerializedName("city")
    private String city;

    @SerializedName("company")
    private Integer companyId;

    @Override
    protected ApiRequestBody setFields(String requestString) {
        UpdateUserInfoRequest request = JsonUtil.jsonStr2Bean(requestString, UpdateUserInfoRequest.class);
        this.account = request.getAccount();
        this.fullName = request.getFullName();
        this.phone = request.getPhone();
        this.address1 = request.getAddress1();
        this.address2 = request.getAddress2();
        this.city = request.getCity();
        this.companyId = request.getCompanyId();
        return request;
    }
}
