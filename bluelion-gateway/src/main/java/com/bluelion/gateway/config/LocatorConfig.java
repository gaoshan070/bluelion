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
                        .filters(f -> f.filters(authFilter).stripPrefix(1))
                        .uri("lb://bluelion-usercenter")
                        .order(0)
                        .id("user")
                ).build();
    }
}
