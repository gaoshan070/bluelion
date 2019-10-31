package com.bluelion.gateway.entity;

import lombok.Data;

@Data
public class SafeInfo {

    private String requestSource;
    private String ip;
    private String keyGroup;
    private String methodName;
    private String validTokenOnlyUserIdRs;
    private int isClient;
}
