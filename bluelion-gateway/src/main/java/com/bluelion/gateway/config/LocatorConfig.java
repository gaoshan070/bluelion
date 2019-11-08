package com.bluelion.gateway.config;

import com.bluelion.gateway.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocatorConfig {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    @ConditionalOnBean(AuthFilter.class)
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/user/**")
                        .filters(f -> f.filters(authFilter).stripPrefix(1)
                                .hystrix(config -> config
                                .setName("myHystrix")
                                .setFallbackUri("forward:/defaultfallback/user")))
                        .uri("lb://bluelion-usercenter")
                        .order(0)
                        .id("user")
                ).route(r -> r.path("/order/**")
                        .filters(f -> f.filters(authFilter).stripPrefix(1)
                                .hystrix(config -> config
                                .setName("myHystrix")
                                .setFallbackUri("forward:/defaultfallback/order")))
                        .uri("lb://bluelion-order")
                        .order(0)
                        .id("order")
                ).route(r -> r.path("/content/**")
                        .filters(f -> f.filters(authFilter).stripPrefix(1)
                                .hystrix(config -> config
                                .setName("myHystrix")
                                .setFallbackUri("forward:/defaultfallback/content")))
                        .uri("lb://bluelion-content")
                        .order(0)
                        .id("content")
                ).build();
    }
}
