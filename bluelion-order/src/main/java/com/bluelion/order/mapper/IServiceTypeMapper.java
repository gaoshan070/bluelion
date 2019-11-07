package com.bluelion.order.mapper;

import com.bluelion.shared.model.ServiceType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IServiceTypeMapper {

    int existService(@Param("serviceId") Integer serviceId);

    List<ServiceType> serviceList();
}
