package com.example.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimeAspect {
    @Pointcut("within(@com.example.aspect.ExecutionTime *)")
    public void beanAnnotated() {

    }

    @Pointcut("@annotation(com.example.aspect.ExecutionTime)")
    public void methodAnnotated() {

    }

    @Around("beanAnnotated() || methodAnnotated()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        log.info("Метод {} выполняется за {} миллисекунд", joinPoint.getSignature().getName(), elapsedTime);
        return result;
    }
}
