package com.bluelion.gateway.controller;

import com.bluelion.shared.utils.ServiceResultUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DefaultHystrixController {

    @RequestMapping("/defaultfallback/{serviceName}")
    public Mono<String> fallback(@PathVariable("serviceName") String serviceName) {
        return Mono.just(ServiceResultUtil.serverError("Sorry, "+ serviceName +" service doesn't work!").toString());
    }
}
