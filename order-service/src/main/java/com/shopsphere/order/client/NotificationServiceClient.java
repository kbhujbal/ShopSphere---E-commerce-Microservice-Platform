package com.shopsphere.order.client;

import com.shopsphere.common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@FeignClient(name = "notification-service")
public interface NotificationServiceClient {
    @PostMapping("/api/v1/notifications")
    ApiResponse<?> createNotification(@RequestBody Map<String, Object> notification);
}
