package com.bluelion.shared.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class PrintTemplateParams {

    @SerializedName("speed")
    private Integer speed;


}
