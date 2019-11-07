package com.bluelion.order.api;

import com.bluelion.order.request.CreateOrderRequest;
import com.bluelion.order.service.PrintOrderService;
import com.bluelion.order.service.ServiceTypeService;
import com.bluelion.shared.helper.IMethodService;
import com.bluelion.shared.model.*;
import com.bluelion.shared.utils.DateUtil;
import com.bluelion.shared.utils.ServiceResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class CreateOrderService extends OrderBaseService implements IMethodService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PrintOrderService printOrderService;

    @Autowired
    ServiceTypeService serviceTypeService;

    @Override
    public ApiRequestBody getRequestBody(BaseRequest baseRequest) throws Exception {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.parseRequest(baseRequest);
        return createOrderRequest;
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
        return "create_order";
    }

    @Override
    public Result execute4Server() {
        return null;
    }

    @Override
    public Result execute4Client(ApiRequestBody apiRequestBody, User user) throws Exception {
        CreateOrderRequest createOrderRequest = (CreateOrderRequest) apiRequestBody;
        return dealCreateOrder(createOrderRequest, user);
    }

    private Result dealCreateOrder(CreateOrderRequest createOrderRequest, User user) {
        if(createOrderRequest.getUserId() == null
            || createOrderRequest.getUserId() <= 0
            || StringUtils.isEmpty(createOrderRequest.getPlateNumber())
            || StringUtils.isEmpty(createOrderRequest.getNextServiceDue())
            || createOrderRequest.getServiceId() == null
            || user == null) {
            logger.error("创建打印请求参数错误");
            return ServiceResultUtil.illegal("Invalid Params");
        }
        boolean isServiceExisted = serviceTypeService.existService(createOrderRequest.getServiceId());
        if(!isServiceExisted) {
            logger.error("打印服务类型不存在");
            return ServiceResultUtil.illegal("This type of service is not existed");
        }
        PrintOrder printOrder = new PrintOrder();
        printOrder.setMid(user.getId());
        printOrder.setPlateNumber(createOrderRequest.getPlateNumber());
        printOrder.setNextService(createOrderRequest.getNextServiceDue());
        printOrder.setServiceId(createOrderRequest.getServiceId());
        Date printDate = null;
        try {
            printDate = DateUtil.parse(createOrderRequest.getPrintDate(), DateUtil.YYYY_MM_DD);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        printOrder.setPrintDate(printDate);
        printOrderService.createPrintOrder(printOrder);
        return ServiceResultUtil.success();
    }
}
