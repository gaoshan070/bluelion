package com.bluelion.shared.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Advertise {

    private Integer id;
    private String title;
    private int type;
    private String url;
    private String image;
    private int position;
    @SerializedName("show_time")
    private int showTime;
    private int status;

}
