package com.example.Task_3.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.example.Task_3.service..*(..)) || execution(* com.example.Task_3.controller..*(..))")
    public void applicationLayer() {}

    @Before("applicationLayer()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering: {} with arguments = {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "applicationLayer()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Completed: {} with result = {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(pointcut = "applicationLayer()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        logger.error("Exception in: {} with error = {}", joinPoint.getSignature(), ex.getMessage());
    }
}

