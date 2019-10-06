package com.bluelion.shared.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DeviceInfo {

    @SerializedName("mac")
    private String mac;
    @SerializedName("imei")
    private String imei;
    @SerializedName("imsi")
    private String imsi;
}
