package com.dealerstat.exception;

import lombok.Getter;

@Getter
public class ApiRequestException extends RuntimeException {
    private final ApiException apiException;

    public ApiRequestException(ApiException apiException) {
        this.apiException = apiException;
    }
}
