package com.bluelion.gateway.mapper;

import com.bluelion.gateway.entity.SafeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ISafeInfoMapper {

    SafeInfo getSafeInfoByRequestSource(String requestSource);

//    int getCountInVipBlackList(int userId);

    Integer getDeviceLoginErrorCount(@Param("deviceNo") String deviceNo, @Param("date") String date);

    Integer getDeviceLoginBlacklistStatus(String deviceNo);

    void insertDeviceLoginError(@Param("deviceNo") String deviceNo, @Param("date") String date);

    void insertDeviceLoginBlacklist(@Param("deviceNo") String deviceNo, @Param("userId") int userId,
                                    @Param("osName") String osName);

    int updateDeviceLoginErrorCount(@Param("deviceNo") String deviceNo, @Param("date") String date,
                                    @Param("count") int count);

    int updateDeviceBlacklistStatus(@Param("status") int status, @Param("deviceNo") String deviceNo);

}
