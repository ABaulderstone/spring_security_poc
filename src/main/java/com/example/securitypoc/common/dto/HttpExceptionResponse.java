package com.example.securitypoc.common.dto;

import com.example.securitypoc.common.exception.ServiceError;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpExceptionResponse {
    private String errorType;
    private String message;
    private int status;
    private ServiceError details;

    public HttpExceptionResponse(String errorType, String message, int status, ServiceError details) {
        this.errorType = errorType;
        this.message = message;
        this.status = status;
        this.details = details;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ServiceError getDetails() {
        return details;
    }

    public void setDetails(ServiceError details) {
        this.details = details;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
