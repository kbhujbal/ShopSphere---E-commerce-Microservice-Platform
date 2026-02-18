package com.shopsphere.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Value("${services.user.url}")
    private String userServiceUrl;

    @Value("${services.product.url}")
    private String productServiceUrl;

    @Value("${services.order.url}")
    private String orderServiceUrl;

    @Value("${services.cart.url}")
    private String cartServiceUrl;

    @Value("${services.payment.url}")
    private String paymentServiceUrl;

    @Value("${services.shipping.url}")
    private String shippingServiceUrl;

    @Value("${services.notification.url}")
    private String notificationServiceUrl;

    @Value("${services.review.url}")
    private String reviewServiceUrl;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // User Service - Auth endpoints
                .route("auth-service", r -> r
                        .path("/api/v1/auth/**")
                        .uri(userServiceUrl))

                // User Service - User endpoints
                .route("user-service", r -> r
                        .path("/api/v1/users/**")
                        .uri(userServiceUrl))

                // Product Service
                .route("product-service", r -> r
                        .path("/api/v1/products/**")
                        .uri(productServiceUrl))

                // Order Service
                .route("order-service", r -> r
                        .path("/api/v1/orders/**")
                        .uri(orderServiceUrl))

                // Cart Service
                .route("cart-service", r -> r
                        .path("/api/v1/carts/**")
                        .uri(cartServiceUrl))

                // Payment Service
                .route("payment-service", r -> r
                        .path("/api/v1/payments/**")
                        .uri(paymentServiceUrl))

                // Shipping Service
                .route("shipping-service", r -> r
                        .path("/api/v1/shipments/**")
                        .uri(shippingServiceUrl))

                // Notification Service
                .route("notification-service", r -> r
                        .path("/api/v1/notifications/**")
                        .uri(notificationServiceUrl))

                // Review Service
                .route("review-service", r -> r
                        .path("/api/v1/reviews/**")
                        .uri(reviewServiceUrl))

                .build();
    }
}
