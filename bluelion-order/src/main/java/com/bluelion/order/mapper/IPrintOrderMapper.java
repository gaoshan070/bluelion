package com.bluelion.order.mapper;

import com.bluelion.shared.model.PrintOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface IPrintOrderMapper {

    void createPrintOrder(@Param("userId") Integer userId, @Param("plateNumber") String plateNumber,
                          @Param("serviceId") Integer serviceId, @Param("nextService") String nextService,
                          @Param("printDate") Date printDate);

    List<PrintOrder> printOrderHistory(@Param("userId") Integer userId);
}
