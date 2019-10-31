package com.bluelion.shared.model;

import com.bluelion.shared.constants.EncodeMethod;
import com.bluelion.shared.constants.KeyGroup;
import com.bluelion.shared.utils.CodecUtils;

public abstract class ApiRequestBody extends BaseRequest {

    public ApiRequestBody parseRequest(BaseRequest request) throws Exception {
        try {
            KeyGroup keyGroup = KeyGroup.DEFAULT;
            String requestString;
            if (EncodeMethod.AES.equals(keyGroup.getEncodeMethod())) {
                requestString = CodecUtils.aesDecode(request.getParams(), keyGroup);
            } else {
                throw new Exception("非法加密方法");
            }
            String mySign = CodecUtils.getMySign(requestString, keyGroup);
            if (mySign.equals(request.getSign())) {
                ApiRequestBody requestBody = setFields(requestString);
                this.deviceInfo = requestBody.getDeviceInfo();
                this.otherInfo = requestBody.getOtherInfo();
                return requestBody;
            } else {
                throw new Exception("签名错误");
            }
        } catch (Exception e) {
            throw e;
        }

    }
    /**
     * 设置子类请求字段
     */
    protected abstract ApiRequestBody setFields(String requestString);
}
