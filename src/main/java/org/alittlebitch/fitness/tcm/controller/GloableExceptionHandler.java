package org.alittlebitch.fitness.tcm.controller;

import org.shoper.commons.responseentity.BaseResponse;
import org.shoper.commons.responseentity.ResponseBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ShawnShoper
 * @date 2018/6/25 14:30
 */
@RestControllerAdvice
public class GloableExceptionHandler {
    @ExceptionHandler(IllegalAccessException.class)
    public HttpEntity<BaseResponse> illegalAccessExceptionHandle(Exception e) {
        return new HttpEntity<BaseResponse>(ResponseBuilder.custom().failed(e.getMessage(), 501).build());
    }

    @ExceptionHandler(Exception.class)
    public HttpEntity<BaseResponse> exceptionHandle(Exception e) {
        return new HttpEntity<BaseResponse>(ResponseBuilder.custom().failed(e.getMessage(), 500).build());
    }
}
