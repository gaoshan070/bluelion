package com.bluelion.order.repository;

import com.bluelion.order.mapper.IServiceTypeMapper;
import com.bluelion.shared.model.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServiceTypeRepository {

    @Autowired
    private IServiceTypeMapper serviceTypeMapper;

    public boolean existService(Integer serviceId) {
        int count = serviceTypeMapper.existService(serviceId);
        return count > 0;
    }

    public List<ServiceType> list() {
        return serviceTypeMapper.serviceList();
    }
}
