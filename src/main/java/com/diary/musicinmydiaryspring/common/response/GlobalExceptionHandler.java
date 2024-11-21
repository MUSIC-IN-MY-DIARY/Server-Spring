package com.diary.musicinmydiaryspring.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<BaseResponse<Void>> handleCustomRuntimeException(CustomRuntimeException ex) {
        BaseResponse<Void> errorResponse = new BaseResponse<>(ex.getStatus());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getStatus().getCode()));
    }
}