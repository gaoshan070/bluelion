package com.bluelion.order.service;

import com.bluelion.order.repository.PrintOrderRepository;
import com.bluelion.shared.model.PrintOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrintOrderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PrintOrderRepository printOrderRepository;

    public boolean createPrintOrder(PrintOrder printOrder) {
        if(printOrder == null) {
            logger.error("打印请求为空");
            return false;
        }
        printOrderRepository.createPrintOrder(printOrder);
        logger.info("创建打印请求成功");
        return true;
    }

    public List<PrintOrder> printOrderHistory(Integer userId) {
        return printOrderRepository.printOrderHistory(userId);
    }
}
