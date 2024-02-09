package com.example.aspect;

import org.slf4j.event.Level;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})// ставить над классами и над методами
@Retention(RetentionPolicy.RUNTIME)// чтобы антотация была доступна в runtime
public @interface Loggable {
}
