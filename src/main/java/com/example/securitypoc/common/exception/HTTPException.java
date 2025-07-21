package com.example.securitypoc.common.exception;

import org.springframework.http.HttpStatus;

public class HTTPException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final ServiceError serviceError;

    public HTTPException(HttpStatus httpStatus, ServiceError serviceError) {
        super(serviceError != null ? serviceError.getMessage() : httpStatus.toString());
        this.httpStatus = httpStatus;

        this.serviceError = serviceError;
    }

    public HTTPException(HttpStatus httpStatus) {
        this(httpStatus, null);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ServiceError getServiceError() {
        return serviceError;
    }

    public boolean hasServiceError() {
        return serviceError != null;
    }

}
