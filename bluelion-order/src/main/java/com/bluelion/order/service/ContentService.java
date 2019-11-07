package com.bluelion.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
@FeignClient(value = "bluelion-content")
public interface ContentService {

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    String orderInfo(@PathVariable String id);
}
