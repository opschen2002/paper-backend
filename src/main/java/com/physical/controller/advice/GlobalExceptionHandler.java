package com.physical.controller.advice;

import com.physical.entity.CommonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public CommonResult<?> handleException(Exception e) {
        return CommonResult.error(e.getMessage());
    }
}