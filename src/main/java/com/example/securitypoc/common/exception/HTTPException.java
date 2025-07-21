package com.example.securitypoc.common.exception;

import org.springframework.http.HttpStatus;

public class HTTPException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String errorType;
    private final ServiceError serviceError;

    public HTTPException(HttpStatus httpStatus, String errorType, ServiceError serviceError) {
        super(errorType);
        this.httpStatus = httpStatus;
        this.errorType = errorType;
        this.serviceError = serviceError;
    }

    public HTTPException(HttpStatus httpStatus, String errorType) {
        this(httpStatus, errorType, null);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorType() {
        return errorType;
    }

    public ServiceError getServiceError() {
        return serviceError;
    }

    public boolean hasServiceError() {
        return serviceError != null;
    }

}
