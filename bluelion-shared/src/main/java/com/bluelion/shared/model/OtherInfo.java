package com.bluelion.shared.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class OtherInfo {

    @SerializedName("app_id")
    private String appId;
}
