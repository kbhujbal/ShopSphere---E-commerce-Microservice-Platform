package com.shopsphere.common.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String message) {
        super(message, "RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, "RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND, cause);
    }
} 