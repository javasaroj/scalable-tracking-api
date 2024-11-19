package com.valuelabs.app.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogPayloadAdvice {

    @Around("@annotation(com.valuelabs.app.annotation.LogPayloads)")
    public Object logPayload(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Request Body {}", new ObjectMapper().writeValueAsString(joinPoint.getArgs()));
        Object obj = joinPoint.proceed();
        log.info("Response Body {}", new ObjectMapper().writeValueAsString(obj));
        return obj;
    }
}
