package com.expenseflow.common.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("""
            execution(* com.expenseflow..controller..*(..)) ||
            execution(* com.expenseflow..service..*(..)) ||
            execution(* com.expenseflow..repository..*(..))
            """)
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.info("Entering method: {}.{} with arguments: {}",
                className,
                methodName,
                Arrays.toString(joinPoint.getArgs()));

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        log.info("Exiting method: {}.{} with result: {} in {} ms",
                className,
                methodName,
                result,
                executionTime);

        return result;
    }
}