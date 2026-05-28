package com.expenseflow.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    private static final long SLOW_API_THRESHOLD = 1000;

    @Around("execution(* com.expenseflow..service..*(..))")
    public Object monitorExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        if (executionTime > SLOW_API_THRESHOLD) {

            log.warn(
                    "Slow method execution detected: {} took {} ms",
                    joinPoint.getSignature(),
                    executionTime
            );
        }

        return result;
    }
}