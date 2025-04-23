package com.sample.coinchange.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAOP {
    @AfterThrowing(pointcut = "execution(* com.sample.coinchange..*.*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.error("Exception in {}.{}: {} - {}",
                className, methodName, ex.getClass().getSimpleName(), ex.getMessage());
    }
}
