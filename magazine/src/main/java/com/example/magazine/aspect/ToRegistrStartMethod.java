package com.example.magazine.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотациия для регистрации начала выполнения метода
 */
@Retention(RetentionPolicy.RUNTIME) //Время существование аннотации
@Target(ElementType.METHOD) //Цель отлова
public @interface ToRegistrStartMethod {
}
