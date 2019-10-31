package com.bluelion.shared.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class BaseRequest {

    /**
     * 设备信息
     */
    @SerializedName("device_info")
    protected DeviceInfo deviceInfo;
    /**
     * 其他信息
     */
    @SerializedName("other_info")
    protected OtherInfo otherInfo;

    /**
     * 签名
     */
    @SerializedName("sign")
    protected String sign;

    /**
     * 请求参数
     */
    @SerializedName("params")
    protected String params;

    /**
     * 请求来源
     */
    @SerializedName("request_source_index")
    private String requestSource;

    @SerializedName("version")
    private String version;

}
