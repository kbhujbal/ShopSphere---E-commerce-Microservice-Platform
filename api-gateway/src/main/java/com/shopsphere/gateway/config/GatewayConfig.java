package com.shopsphere.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // User Service - Auth endpoints
                .route("auth-service", r -> r
                        .path("/api/v1/auth/**")
                        .uri("lb://user-service"))

                // User Service - User endpoints
                .route("user-service", r -> r
                        .path("/api/v1/users/**")
                        .uri("lb://user-service"))

                // Product Service
                .route("product-service", r -> r
                        .path("/api/v1/products/**")
                        .uri("lb://product-service"))

                // Order Service
                .route("order-service", r -> r
                        .path("/api/v1/orders/**")
                        .uri("lb://order-service"))

                // Cart Service
                .route("cart-service", r -> r
                        .path("/api/v1/carts/**")
                        .uri("lb://cart-service"))

                // Payment Service
                .route("payment-service", r -> r
                        .path("/api/v1/payments/**")
                        .uri("lb://payment-service"))

                // Shipping Service
                .route("shipping-service", r -> r
                        .path("/api/v1/shipments/**")
                        .uri("lb://shipping-service"))

                // Notification Service
                .route("notification-service", r -> r
                        .path("/api/v1/notifications/**")
                        .uri("lb://notification-service"))

                // Review Service
                .route("review-service", r -> r
                        .path("/api/v1/reviews/**")
                        .uri("lb://review-service"))

                .build();
    }
}
