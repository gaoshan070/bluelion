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

    @SerializedName("os")
    private int os;
    @SerializedName("ios_info")
    private IosInfo iosInfo;
    @SerializedName("android_info")
    private AndroidInfo androidInfo;

    @Data
    public class IosInfo {

        @SerializedName("udid")
        private String udid;
        @SerializedName("open_udid")
        private String openUdid;
        @SerializedName("idfa")
        private String idfa;
        @SerializedName("idfv")
        private String idfv;
        @SerializedName("ios_model")
        private int iosModel;
        @SerializedName("breakout")
        private int breakout;
    }

    @Data
    public class AndroidInfo {

        @SerializedName("android_id")
        private String androidId;
        @SerializedName("android_serial_number")
        private String androidSerialNumber;

    }
}
