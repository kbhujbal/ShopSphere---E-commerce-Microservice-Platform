package com.shopsphere.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {
    private String code;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> errors;
} 