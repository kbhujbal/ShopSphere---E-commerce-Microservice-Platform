package com.shopsphere.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super(message, "BAD_REQUEST", HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, "BAD_REQUEST", HttpStatus.BAD_REQUEST, cause);
    }
} 