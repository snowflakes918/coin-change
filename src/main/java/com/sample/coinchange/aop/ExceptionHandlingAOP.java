package com.sample.coinchange.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionHandlingAOP {

    @AfterThrowing(pointcut = "execution(* com.sample.coinchange.controller..*(..))", throwing = "ex")
    public ResponseEntity<Object> handleControllerExceptions(Exception ex) {
        if (ex instanceof IllegalArgumentException) {
            log.error("IllegalArgumentException caught: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        } else if (ex instanceof IllegalStateException) {
            log.error("IllegalStateException caught: {}", ex.getMessage());
            return ResponseEntity.status(500).body("Internal server error: " + ex.getMessage());
        } else {
            log.error("Unexpected exception caught: {}", ex.getMessage());
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }
}