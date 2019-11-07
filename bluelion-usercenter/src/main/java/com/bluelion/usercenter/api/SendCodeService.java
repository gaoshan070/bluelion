package com.bluelion.usercenter.api;

import com.bluelion.shared.constants.UserConstants;
import com.bluelion.shared.helper.IMethodService;
import com.bluelion.shared.model.*;
import com.bluelion.shared.utils.*;
import com.bluelion.usercenter.cache.ISendSmsOrEmailCacheManager;
import com.bluelion.usercenter.constants.SendCodeType;
import com.bluelion.usercenter.request.SendCodeRequest;
import com.bluelion.usercenter.service.ValidateCodeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SendCodeService extends UserCenterBaseService implements IMethodService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ISendSmsOrEmailCacheManager sendSmsOrEmailCacheManager;

    @Autowired
    private ValidateCodeService validateCodeService;

    @Override
    public ApiRequestBody getRequestBody(BaseRequest baseRequest) throws Exception {
        SendCodeRequest sendCodeRequest = new SendCodeRequest();
        sendCodeRequest.parseRequest(baseRequest);
        return sendCodeRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(ApiRequestBody apiRequestBody) {
        return null;
    }

    @Override
    public String getMethodName() {
        return "send_code";
    }

    @Override
    public Result execute4Server() {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result execute4Client(ApiRequestBody apiRequestBody, User user) throws Exception {
        SendCodeRequest sendCodeRequest = (SendCodeRequest) apiRequestBody;
        if (StringUtils.isEmpty(sendCodeRequest.getAccount())) {
            return ServiceResultUtil.illegal("Invalid params");
        }
        return dealSendCode(sendCodeRequest);
    }

    private Result dealSendCode(SendCodeRequest sendCodeRequest) throws Exception {
        if(isClientRequest(sendCodeRequest)) {
            String device = getDeviceNo4Client(sendCodeRequest);
            int sendCount = sendSmsOrEmailCacheManager.getSendCount(device);
            logger.info("设备发送次数:" + sendCount);
            if (sendCount >= UserConstants.SEND_SMS_OR_EMAIL_COUNT_EVERY_DAY) {
                return ServiceResultUtil.illegal("Sending code too often, please try again tomorrow");
            }
        }
        String validationCode = CodecUtils.generateValidateCode();
        logger.info("生成的验证码 : " + validationCode);
        if (ValidateUtil.isEmail(sendCodeRequest.getAccount())) {
            logger.info("下发到邮箱");
            if (sendCodeRequest.getType() == SendCodeType.LOST_PASSWORD.getIndex()) {
                logger.info("忘记密码请求");
                return dealLostPwdEmail(sendCodeRequest, validationCode);
            } else if (sendCodeRequest.getType() == SendCodeType.MODIFY_PASSWORD.getIndex()) {
                logger.info("修改密码请求");
                return dealModifyPwdEmail(sendCodeRequest, validationCode);
            } else {
                return ServiceResultUtil.illegal("Unsupported Type");
            }
        } else {
            return ServiceResultUtil.illegal("Invalid email");
        }
    }

    private Result dealModifyPwdEmail(SendCodeRequest sendCodeRequest,
                                             String validationCode) throws Exception {
        User user = userBaseService.getDetailByEmail(sendCodeRequest.getAccount());
        if (user == null) {
            return ServiceResultUtil.illegal("Wrong account");
        }
        if (!sendCodeRequest.getAccount().equals(user.getEmail())) {
            return ServiceResultUtil.illegal("The email is not match your account");
        }
        String errorMessage = getUserStatusErrorMessage(user);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        if (!validateRequestToken(user.getId(), sendCodeRequest)) {
            return ServiceResultUtil.tokenExpired();
        }
        return sendEmail(sendCodeRequest, validationCode, user, SendCodeType.MODIFY_PASSWORD);
    }

    private Result dealLostPwdEmail(SendCodeRequest sendCodeRequest,
                                           String validationCode) throws Exception {
        User user = userBaseService.getDetailByEmail(sendCodeRequest.getAccount());

        String errorMessage = getUserStatusErrorMessage(user);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        return sendEmail(sendCodeRequest, validationCode, user, SendCodeType.LOST_PASSWORD);
    }

    private Result sendEmail(SendCodeRequest sendCodeRequest,
                                    String validationCode, User user, SendCodeType sendCodeType) throws Exception {
        ValidateCode validateCode = validateCodeService.getValidateCode4Email(sendCodeRequest.getAccount(),
                sendCodeType);
        if (validateCode != null) {
            String errorMessage = validateCodeTime(validateCode.getVcodeTime());
            if (StringUtils.isNotEmpty(errorMessage)) {
                return ServiceResultUtil.illegal(errorMessage);
            }
        }
        String content;
        String title = "BlueLion";

        content = validationCode + UserConstants.SEND_CODE_TEMPLATE_4_EMAIL;
        if (EmailUtil.sendEmail(sendCodeRequest.getAccount(), title, content)) {
            logger.info("发送邮件成功");
            validateCodeService.insertOrUpdateValidateCode4Email(sendCodeRequest.getAccount(), validationCode,
                    sendCodeType);
            logger.info("更新用户验证码和发送时间成功");
            updateSendCount(getDevice4Client(sendCodeRequest));
            return ServiceResultUtil.success();
        }
        return ServiceResultUtil.illegal("邮件发送失败");
    }

    private void updateSendCount(String device) {
        boolean result = sendSmsOrEmailCacheManager.updateSendCount(device);
        logger.info("发送次数加1结果:" + result);
    }

    private String validateCodeTime(long validateCodeTime) {
        if (DateUtil.getCurrentTimeSeconds() - validateCodeTime < UserConstants.VALIDATION_CODE_SEND_INTERVAL_SECONDS) {
            return "The interval is less than" + UserConstants.VALIDATION_CODE_SEND_INTERVAL_SECONDS + "seconds";
        }
        return "";
    }

    private String getDevice4Client(SendCodeRequest sendCodeRequest) {
        try {
            if (!isClientRequest(sendCodeRequest))
                return "";
            String device = "";
            Os os = getClientOsByRequest(sendCodeRequest);
            if (Os.IOS.equals(os)) {
                device = sendCodeRequest.getDeviceInfo().getIosInfo().getIdfa();
            } else if (Os.ANDROID.equals(os)) {
                device = sendCodeRequest.getDeviceInfo().getAndroidInfo().getAndroidId();
            }
            return device;
        } catch (Exception e) {
            logger.error("获取设备标识错误");
        }
        return "";
    }
}
