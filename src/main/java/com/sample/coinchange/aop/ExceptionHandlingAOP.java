package com.sample.coinchange.aop;

import com.sample.coinchange.dto.ResponseType;
import com.sample.coinchange.dto.ResultEnum;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlingAOP {

    @ExceptionHandler(IllegalArgumentException.class)
    public <T> ResponseType<T> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseType.error(ResultEnum.BAD_INPUT, ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public <T> ResponseType<T> handleIllegalStateException(IllegalStateException ex) {
        return ResponseType.error(ResultEnum.FAILED, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public <T> ResponseType<T> handleOtherException(Exception ex) {
        return ResponseType.error(ResultEnum.ERROR, ex.getMessage());
    }
}