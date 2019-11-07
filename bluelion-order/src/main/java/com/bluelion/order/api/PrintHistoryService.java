package com.bluelion.order.api;

import com.bluelion.order.request.CreateOrderRequest;
import com.bluelion.order.request.PrintHistoryRequest;
import com.bluelion.order.response.PrintHistoryResponse;
import com.bluelion.order.service.PrintOrderService;
import com.bluelion.shared.helper.IMethodService;
import com.bluelion.shared.model.*;
import com.bluelion.shared.utils.JsonUtil;
import com.bluelion.shared.utils.ServiceResultUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrintHistoryService extends OrderBaseService implements IMethodService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PrintOrderService printOrderService;

    @Override
    public ApiRequestBody getRequestBody(BaseRequest baseRequest) throws Exception {
        PrintHistoryRequest printHistoryRequest = new PrintHistoryRequest();
        printHistoryRequest.parseRequest(baseRequest);
        return printHistoryRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(ApiRequestBody apiRequestBody) {
        CreateOrderRequest createOrderRequest = (CreateOrderRequest) apiRequestBody;
        User user = userService.userDetail(createOrderRequest.getUserId());
        return user;
    }

    @Override
    public String getMethodName() {
        return "print_order_history";
    }

    @Override
    public Result execute4Server() {
        return null;
    }

    @Override
    public Result execute4Client(ApiRequestBody apiRequestBody, User user) throws Exception {
        PrintHistoryRequest printHistoryRequest = (PrintHistoryRequest) apiRequestBody;
        return dealPrintHistory(printHistoryRequest, user);
    }

    private Result dealPrintHistory(PrintHistoryRequest printHistoryRequest, User user) {
        logger.info("处理打印订单历史请求");
        if(printHistoryRequest.getUserId() == null
                || user == null) {
            logger.error("打印记录请求参数不正确");
            return ServiceResultUtil.illegal("Invalid Params");
        }
        List<PrintOrder> printHistory = Lists.newArrayList();

        printHistory = printOrderService.printOrderHistory(user.getId());
        PrintHistoryResponse printHistoryResponse = new PrintHistoryResponse();
        printHistoryResponse.setPrintOrderList(printHistory);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(printHistoryResponse));
    }

}
