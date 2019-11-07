package com.bluelion.usercenter.response;

import com.bluelion.shared.model.User;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class LoginResponse {

    @SerializedName("user_id")
    private Integer userId = 0;
    @SerializedName("user_name")
    private String userName = "";
    @SerializedName("email")
    private String email = "";
    @SerializedName("phone")
    private String phone = "";
    @SerializedName("token")
    private String token = "";

    public void parseFromUser(User user) {
        if(user == null)
            return;
        setUserId(user.getId());
        setUserName(user.getUserName());
        setPhone(user.getPhone());
        setEmail(user.getEmail());
        setToken(user.getToken());
    }

    public void setPhone(String phone) {
        if (StringUtils.isEmpty(phone))
            return;
        this.phone = phone;
    }
    public void setToken(String token) {
        if (StringUtils.isEmpty(token))
            return;
        this.token = token;
    }
    public void setUserName(String userName) {
        if (StringUtils.isEmpty(userName))
            return;
        this.userName = userName;
    }
}
