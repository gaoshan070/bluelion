package com.bluelion.shared.model;

import com.bluelion.shared.enums.ResultCodeEnum;
import com.google.gson.annotations.SerializedName;

public class Result<T> {
        @SerializedName("code")
        private String code = ResultCodeEnum.SUCCESS.getCode();
        @SerializedName("msg")
        private String msg;
        @SerializedName("data")
        private T data;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
}
