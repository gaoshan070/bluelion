package com.bluelion.usercenter.mapper;

import com.bluelion.usercenter.entity.User;
import com.bluelion.usercenter.entity.UserToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IUserMapper {

    void addUser(@Param("userName") String userName,
                 @Param("nickName") String nickName, @Param("password") String password,
                 @Param("salt") String salt, @Param("email") String email);

    User login(String username, String password);

    User detail(@Param("userId") Integer userId);

    void insertUserToken(@Param("userId") Integer userId, @Param("validateToken") String validateToken,
                         @Param("token") String token, @Param("device") String device,
                         @Param("requestSource") String requestSource);

    int updateUserToken(@Param("userId") Integer userId, @Param("validateToken") String validateToken,
                        @Param("token") String token, @Param("device") String device,
                        @Param("requestSource") String requestSource);

    int existUserToken(@Param("userId") Integer userId);

    UserToken getUserToken(@Param("userId") Integer userId, @Param("expiredTime") long expiredTime);

    String getUserVToken(@Param("userId") int userId);

    int updateUserValidateToken(@Param("validateToken") String validateToken, @Param("userId") Integer userId);

    int clearUserToken(Integer userId);
}
