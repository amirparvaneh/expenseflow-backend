package com.expenseflow.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ExceptionLoggingAspect {


    @AfterThrowing(
            pointcut = "execution(* com.expenseflow..*(..))",
            throwing = "ex"
    )
    public void logException(
            JoinPoint joinPoint,
            Exception ex) {
        log.error(
                "Exception in {}.{} with arguments {} : {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()),
                ex.getMessage(),
                ex
        );
    }
}
