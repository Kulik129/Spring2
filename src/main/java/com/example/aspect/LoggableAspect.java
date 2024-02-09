package com.example.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggableAspect {
    //Аспект который будет искать все что помечено аанотацией Loggable и логгировать.
    // Если над бином то логировать все методы, если над методом то логировать метод.

    // Логируем класс, и значения return type, если было исключение то логировать exception
    @Pointcut("within(@com.example.aspect.Loggable *)")
    public void beanAnnotatedWith() {
    }
    @Pointcut("@annotation(com.example.aspect.Loggable)")
    public void methodAnnotatedWith() {
    }
    @Around("beanAnnotatedWith() || methodAnnotatedWith()")
    public Object loggableAspect(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("target {}", joinPoint.getTarget().getClass());
        log.info("target {}", joinPoint.getTarget().getClass());
        log.info("args {}", Arrays.toString(joinPoint.getArgs()));

        try {
            Object returnValue = joinPoint.proceed();
            log.info("result = {}", returnValue);
            return returnValue;
        } catch (Throwable e) {
            log.info("exception = [{}, {}]", e.getClass(), e.getMessage());
            throw e;
        }
    }
}