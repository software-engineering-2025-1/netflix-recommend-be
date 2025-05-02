package com.netflix.recommend.controller.exception;

import com.netflix.recommend.exception.CustomException;
import com.netflix.recommend.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(errorCode.getMessage(), errorCode.getCode());
    }
}

