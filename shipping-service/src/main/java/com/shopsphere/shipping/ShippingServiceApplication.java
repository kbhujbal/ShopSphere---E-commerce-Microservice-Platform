package com.shopsphere.shipping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ShippingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShippingServiceApplication.class, args);
    }
} 