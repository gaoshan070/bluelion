package com.bluelion.shared.model;

import com.bluelion.shared.constants.EncodeMethod;
import com.bluelion.shared.constants.KeyGroup;
import com.bluelion.shared.enums.ResultCodeEnum;
import com.bluelion.shared.utils.CodecUtils;
import com.bluelion.shared.utils.JsonUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Result {

        @SerializedName("code")
        private String code = ResultCodeEnum.SUCCESS.getCode();
        @SerializedName("msg")
        private String msg = "";
        @SerializedName("data")
        private JsonElement data = new JsonObject();

        public Result() {
            this.code = ResultCodeEnum.SUCCESS.getCode();
            this.data = new JsonObject();
        }

        public Result(String code, JsonElement data, String msg) {
            this.code = code;
            this.data = data;
            this.msg = msg;
        }

        public Result(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Result(String code, JsonElement data) {
            this.data = data;
            this.code = code;
        }

        public Result(JsonElement data) {
            this.data = data;
            this.code = ResultCodeEnum.SUCCESS.getCode();
        }

        public String convert2Result() {
            String responseJsonStr = JsonUtil.bean2JsonStr(this);
            return responseJsonStr;
        }
        public String convert2Result(KeyGroup keyGroup) {
            String result = null;
            try {
                String responseJsonStr = JsonUtil.bean2JsonStr(this);
    //            LogContext.instance().info(JsonUtil.formatJsonStr(responseJsonStr));
                if (EncodeMethod.AES.equals(keyGroup.getEncodeMethod())) {
                    result = CodecUtils.aesEncode(responseJsonStr, keyGroup);
                } else {
    //                LogContext.instance().error("非法加密方法");
                }
            } catch (Exception e) {
    //            LogContext.instance().error(e, "转化回复报文失败");
            } finally {
    //            LogContext.instance().info("---------------请求结束---------------");
            }
            return result;
        }

        public String toString() {
            return JsonUtil.bean2JsonStr(this);
        }
}
