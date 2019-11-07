package com.bluelion.order.service;

import com.bluelion.order.repository.ServiceTypeRepository;
import com.bluelion.shared.model.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTypeService {

    @Autowired
    ServiceTypeRepository serviceTypeRepository;

    public boolean existService(Integer serviceId){
        return serviceTypeRepository.existService(serviceId);
    }

    public List<ServiceType> serviceTypeList() {
        return serviceTypeRepository.list();
    }
}
