package com.bluelion.shared.model;

import lombok.Data;


@Data
public class FeignRequest {

    private SafeInfo safeInfo;
    private BaseRequest baseRequest;
    private Integer userId;
}
