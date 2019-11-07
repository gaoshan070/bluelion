package com.bluelion.usercenter.mapper;

import com.bluelion.shared.model.ValidateCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IValidateCodeMapper {

    ValidateCode getValidateCode(@Param("mobileOrEmail") String mobileOrEmail, @Param("purpose") int purpose,
                                 @Param("type") int type);

    void insertValidateCode(ValidateCode validateCode);

    int clearValidateCode(@Param("mobileOrEmail") String mobileOrEmail, @Param("purpose") int purpose,
                          @Param("type") int type);

    int updateValidateCode(ValidateCode validateCode);

}
