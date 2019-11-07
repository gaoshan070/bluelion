package com.bluelion.usercenter.mapper;

import com.bluelion.shared.model.User;
import com.bluelion.shared.model.UserToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IUserMapper {

    void addUser(User user);

    void addUserDetail(@Param("userId") Integer userId, @Param("ip") String ip);

    void updateUser(User user);

    User login(String username, String password);

    User detail(@Param("userId") Integer userId);

    int getIdByEmail(@Param("email") String email);

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

    void clearLoginError(@Param("userId") int userId);

    int updatePassword(@Param("userId") int userId, @Param("encodedPassword") String encodedPassword, @Param("salt") String salt);
}
