package com.example.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UnauthorizationException extends RuntimeException {
    public UnauthorizationException() {
        super();
    }
    public UnauthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
    public UnauthorizationException(String message) {
        super(message);
    }
    public UnauthorizationException(Throwable cause) {
        super(cause);
    }
}