package com.shopsphere.shipping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoRepositories
public class ShippingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShippingServiceApplication.class, args);
    }
} 