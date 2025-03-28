package com.shopsphere.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private final String code;
    private final HttpStatus status;

    public BaseException(String message) {
        super(message);
        this.code = "INTERNAL_SERVER_ERROR";
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public BaseException(String message, String code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.code = "INTERNAL_SERVER_ERROR";
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public BaseException(String message, String code, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.status = status;
    }
} 