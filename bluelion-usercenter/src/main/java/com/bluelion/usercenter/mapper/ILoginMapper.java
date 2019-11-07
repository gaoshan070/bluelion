package com.bluelion.usercenter.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ILoginMapper {

    void clearLoginError(@Param("userId") Integer userId);

    void setLoginError(@Param("userId") Integer userId, @Param("errorCount") Integer errorCount);

    void setLastLoginInfo(@Param("userId") Integer userId, @Param("ip") String ip);
}
