package com.bluelion;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCircuitBreaker
@SpringBootApplication
@MapperScan("com.bluelion.gateway.mapper")
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

//    @Bean
//    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("path_route",p -> p.path("/greeting")
//                        .uri("http://localhost:8501/greeting")).build();
//    }

//    @Bean
//    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/customer/**")
//                        .filters(f -> f.filter(new CustomerGatewayFilter())
//                                .addResponseHeader("X-Response-test", "test"))
//                        .uri("http://httpbin.org:80/get")
//                        .id("customer_filter_router")
//                )
//                .build();
//    }

}


