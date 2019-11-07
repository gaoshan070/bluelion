package com.bluelion.order.response;

import com.bluelion.shared.model.PrintOrder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class PrintHistoryResponse {

    @SerializedName("print_order_list")
    private List<PrintOrder> printOrderList;
}
