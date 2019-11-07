package com.bluelion.usercenter.api;

import com.bluelion.shared.helper.IMethodService;
import com.bluelion.shared.model.*;
import com.bluelion.shared.utils.RequestUtil;
import com.bluelion.shared.utils.ServiceResultUtil;
import com.bluelion.usercenter.constants.SendCodeType;
import com.bluelion.usercenter.request.ModifyPasswordRequest;
import com.bluelion.usercenter.service.ValidateCodeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModifyPasswordService extends UserCenterBaseService implements IMethodService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ValidateCodeService validateCodeService;

    @Override
    public ApiRequestBody getRequestBody(BaseRequest baseRequest) throws Exception {
        ModifyPasswordRequest modifyPasswordRequest = new ModifyPasswordRequest();
        modifyPasswordRequest.parseRequest(baseRequest);
        return modifyPasswordRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(ApiRequestBody apiRequestBody) {
        ModifyPasswordRequest modifyPasswordRequest = (ModifyPasswordRequest) apiRequestBody;
        return userBaseService.getDetailByEmail(modifyPasswordRequest.getAccount());
    }

    @Override
    public String getMethodName() {
        return "modify_password";
    }

    @Override
    public Result execute4Server() {
        return null;
    }

    @Override
    public Result execute4Client(ApiRequestBody apiRequestBody, User user) throws Exception {
        ModifyPasswordRequest modifyPasswordRequest = (ModifyPasswordRequest) apiRequestBody;
        if (StringUtils.isEmpty(modifyPasswordRequest.getAccount())
                || StringUtils.isEmpty(modifyPasswordRequest.getOldPassword())
                || StringUtils.isEmpty(modifyPasswordRequest.getNewPassword())) {
            return ServiceResultUtil.illegal("Invalid params");
        }
        return dealModifyPassword(modifyPasswordRequest, user);
    }

    private Result dealModifyPassword(ModifyPasswordRequest modifyPasswordRequest, User user) {
        if (!User.isValidPassword(modifyPasswordRequest.getNewPassword())) {
            return ServiceResultUtil.illegal("Invalid password");
        }
        boolean checkValidationCode = false;
        if (StringUtils.isNotEmpty(user.getEmail())) {
            checkValidationCode = true;
        }
        if(checkValidationCode) {
           ValidateCode validateCode = validateCodeService.getValidateCode4Email(user.getEmail(),
                    SendCodeType.MODIFY_PASSWORD);
            if (validateCode == null) {
                return ServiceResultUtil.illegal("Validation code is not match this email");
            }
            if (!User.isValidationCodeValid(modifyPasswordRequest.getValidationCode(),
                    validateCode.getVcode(), validateCode.getVcodeTime())) {
                return ServiceResultUtil.illegal("Validation code is incorrect or expired");
            }
        }

        String oldPassword = User.getEncodedPassword(modifyPasswordRequest.getOldPassword(), user.getSalt());
        if (oldPassword.equals(user.getPassword())) {
            String salt = User.generateSalt();
            String encodedNewPassword = User.getEncodedPassword(modifyPasswordRequest.getNewPassword(), salt);
            int updatedRows = userBaseService.updatePassword(user.getId(), encodedNewPassword, salt);
            if (updatedRows > 0) {
                userBaseService.deleteToken(user.getId());
                userBaseService.clearLoginError(user.getId());
                if (checkValidationCode) {
                    logger.info("清除验证码信息");
                    validateCodeService.clearValidateCode4Email(user.getEmail(), SendCodeType.MODIFY_PASSWORD);
                }
                logger.info("修改密码成功");
                return ServiceResultUtil.success();
            } else {
                return ServiceResultUtil.illegal("修改密码失败");
            }
        } else {
            return ServiceResultUtil.illegal("原密码不正确");
        }
    }
}
