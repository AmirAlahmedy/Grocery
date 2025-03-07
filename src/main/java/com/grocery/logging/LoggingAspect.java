package com.grocery.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private LoggerFactoryGrocery loggerFactory;

    @Autowired
    public LoggingAspect(LoggerFactoryGrocery loggerFactory) {
        this.loggerFactory = loggerFactory;
    }

    @Before("execution(* com.grocery..*(..)) && !within(com.grocery.logging..*) && !within(com.grocery.security..*)")
    public void logBefore(JoinPoint joinPoint) {
        Logger logger = loggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.info("Entering method: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.grocery..*(..)) && !within(com.grocery.logging..*) && !within(com.grocery.security..*)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        Logger logger = loggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.info("Exiting method: " + joinPoint.getSignature().getName() + " with result: " + result);
    }
}