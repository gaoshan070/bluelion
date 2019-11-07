package com.bluelion.order.request;

import com.bluelion.shared.model.ApiRequestBody;
import com.bluelion.shared.utils.JsonUtil;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CreateOrderRequest extends ApiRequestBody {

    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("plate_number")
    private String plateNumber;

    @SerializedName("service")
    private Integer serviceId;

    @SerializedName("next_service_due")
    private String nextServiceDue;

    @SerializedName("print_date")
    private String printDate;

    @Override
    protected ApiRequestBody setFields(String requestString) {
        CreateOrderRequest request = JsonUtil.jsonStr2Bean(requestString, CreateOrderRequest.class);
        this.userId = request.getUserId();
        this.plateNumber = request.getPlateNumber();
        this.serviceId = request.getServiceId();
        this.nextServiceDue = request.getNextServiceDue();
        this.printDate = request.getPrintDate();
        return request;
    }
}
