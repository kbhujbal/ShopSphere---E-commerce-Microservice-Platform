package com.shopsphere.product;

import com.shopsphere.product.config.ProductServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoRepositories(basePackages = "com.shopsphere.product.repository")
@EnableElasticsearchRepositories(basePackages = "com.shopsphere.product.repository.elasticsearch")
@Import(ProductServiceConfig.class)
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
} 