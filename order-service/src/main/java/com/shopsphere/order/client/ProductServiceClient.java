package com.shopsphere.order.client;

import com.shopsphere.common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service", url = "${services.product.url}")
public interface ProductServiceClient {
    @GetMapping("/api/v1/products/{id}")
    ApiResponse<?> getProductById(@PathVariable("id") String id);

    @PutMapping("/api/v1/products/{id}/stock")
    ApiResponse<?> updateStock(@PathVariable("id") String id, @RequestParam("quantity") int quantity);
}
