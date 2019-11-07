package com.bluelion.usercenter.repository;

import com.bluelion.shared.model.ValidateCode;
import com.bluelion.usercenter.mapper.IValidateCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ValidateCodeRepository {

    @Autowired
    private IValidateCodeMapper validateCodeMapper;

    public ValidateCode getValidateCode(String mobileOrEmail, int purpose, int type) {
        return validateCodeMapper.getValidateCode(mobileOrEmail, purpose, type);
    }

    public void insertValidateCode(ValidateCode validateCode) {
        validateCodeMapper.insertValidateCode(validateCode);
    }

    public int clearValidateCode(String mobileOrEmail, int purpose, int type) {
        return validateCodeMapper.clearValidateCode(mobileOrEmail, purpose, type);
    }

    public int updateValidateCode(ValidateCode validateCode) {
        return validateCodeMapper.updateValidateCode(validateCode);
    }
}
