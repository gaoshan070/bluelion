package com.bluelion.order.repository;

import com.bluelion.order.mapper.IPrintOrderMapper;
import com.bluelion.shared.model.PrintOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrintOrderRepository {

    @Autowired
    IPrintOrderMapper printOrderMapper;

    public void createPrintOrder(PrintOrder printOrder) {
        printOrderMapper.createPrintOrder(printOrder.getMid(), printOrder.getPlateNumber(), printOrder.getServiceId(),
                printOrder.getNextService(), printOrder.getPrintDate());
    }

    public List<PrintOrder> printOrderHistory(Integer userId) {
        return printOrderMapper.printOrderHistory(userId);
    }
}
