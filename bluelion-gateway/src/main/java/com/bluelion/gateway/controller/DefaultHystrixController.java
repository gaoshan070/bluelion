package com.bluelion.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DefaultHystrixController {

    @RequestMapping("/defaultfallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }
}
