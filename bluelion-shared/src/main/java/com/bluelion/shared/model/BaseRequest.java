package com.bluelion.shared.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class BaseRequest {

    /**
     * 设备信息
     */
    @SerializedName("device_info")
    protected DeviceInfo deviceInfo;
    /**
     * 其他信息
     */
    @SerializedName("other_info")
    protected OtherInfo otherInfo;

    /**
     * 签名
     */
    @SerializedName("sign")
    protected String sign;


    /**
     * 请求参数
     */
    @SerializedName("params")
    protected String params;

    /**
     * 请求来源
     */
    @SerializedName("request_source_index")
    private String requestSource;

    @SerializedName("version")
    private String version;

//    /**
//     * 解析请求对象
//     */
//    public void parseRequest(ServerWebExchange request) throws Exception {
//        this.request = request;
////        LogContext logContext = LogContext.instance();
////        logContext.clearLoggerName();
////        logContext.setLoggerName(getLoggerName());
////        logContext.info("---------------请求开始---------------");
//        try {
//
////            this.method = request.getParameter("method");
//            String param = request.getParameter("param");
//            String sign = request.getParameter("sign");
//            this.requestSource = request.getParameter("request_source_index");
//            if (StringUtils.isEmpty(param)) {
////                logContext.error("请求参数为空");
//                throw new Exception("请求参数为空");
//            }
//            KeyGroup keyGroup = (KeyGroup) request.getAttribute(Constants.REQUEST_KEY_GROUP_NAME);
//            String requestString;
//            if (EncodeMethod.AES.equals(keyGroup.getEncodeMethod())) {
//                requestString = CodecUtils.aesDecode(param, keyGroup);
//            } else {
////                logContext.error("非法加密方法");
//                throw new Exception("非法加密方法");
//            }
////            logContext.info("请求内容(解密):" + requestString.replaceAll("\"[a-zA-Z]*[_]?password\"[\\s]*?:[\\s]*?\"[\\w\\W]*?\"",
////                    "\"(*)password\": \"******\""));
//            String mySign = CodecUtils.getMySign(requestString, keyGroup);
//            if (mySign.equals(sign)) {
//                BaseRequest baseRequest = setFields(requestString);
//                this.appId = baseRequest.getAppId();
//                this.deviceInfo = baseRequest.getDeviceInfo();
//                this.otherInfo = baseRequest.getOtherInfo();
//                this.token = baseRequest.getToken();
//            } else {
////                logContext.error("签名错误");
//                throw new Exception("签名错误");
//            }
//        } catch (Exception e) {
////            logContext.error(e, "转换请求内容错误");
//            throw e;
//        }
//    }
}
