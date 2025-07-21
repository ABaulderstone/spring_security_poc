package com.example.securitypoc.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.securitypoc.common.dto.HttpExceptionResponse;
import com.example.securitypoc.common.exception.HTTPException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HTTPException.class)
    public ResponseEntity<HttpExceptionResponse> handleHttpException(HTTPException ex) {
        HttpExceptionResponse response = new HttpExceptionResponse(ex.getHttpStatus().toString(), ex.getMessage(),
                ex.getHttpStatus().value(), ex.getServiceError());
        return new ResponseEntity<HttpExceptionResponse>(response, ex.getHttpStatus());
    }
}
