package com.bluelion.usercenter.response;

import com.bluelion.usercenter.entity.User;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class LoginResponse {

    @SerializedName("user_id")
    private Integer userId = 0;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("token")
    private String token;

    public void parseFromUser(User user) {
        if(user == null)
            return;
        setUserId(user.getId());
        setUserName(user.getUserName());
        setPhone(user.getPhone());
        setEmail(user.getEmail());
        setToken(user.getToken());
    }
}
