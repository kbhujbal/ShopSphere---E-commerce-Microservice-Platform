package com.shopsphere.order.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan(basePackages = "com.shopsphere.order")
@EnableMongoRepositories(basePackages = "com.shopsphere.order.repository")
public class OrderServiceConfig {
} 