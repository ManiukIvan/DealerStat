package com.dealerstat.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiException {
    private final String message;
    private final HttpStatus status;

    public ApiException(String message,
                        HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
