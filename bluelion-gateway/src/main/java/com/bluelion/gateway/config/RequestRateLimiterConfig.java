package com.bluelion.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 自定义限流标志key，多个纬度
 * exchange对象中获取服务ID， 请求信息，用户信息等
 */
@Component
public class RequestRateLimiterConfig {

    @Bean
    @Primary
    public KeyResolver remoteAddressKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    @Bean
    public KeyResolver apiKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }

}
