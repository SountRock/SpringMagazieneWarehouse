package com.example.warehouse.aspect;

import com.example.warehouse.controller.MetricVariables;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Аспект для регистрации процесса выполнения метода
 */
@Aspect
public class ProcessMethodLoggerAspect {
    @Autowired
    private MetricVariables vars;

    File logProcess;

    public ProcessMethodLoggerAspect(String parentDirectory, String processLoggerFileName) {
        logProcess = new File(parentDirectory);
        logProcess.mkdirs();
        logProcess = new File(parentDirectory, processLoggerFileName);
    }

    /**
     * Отследить начало операции
     * @param joinPoint
     * @throws Throwable
     */
    @Before(value = "@annotation(ToRegistrStartMethod)")
    @Order(1)
    public void catchStartOperation(JoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String temp = "# Method Start [" + methodSignature + "] \n ## With args: " + Arrays.toString(joinPoint.getArgs());

        try(FileWriter writer = new FileWriter(logProcess, true)){
            writer.append(temp);
            writer.append("\n");
        }
    }

    /**
     * Отследить время выполнения
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "@annotation(ToRegistrStartMethod)")
    public Object timeProcessMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();

        long metric = end - start;

        vars.getProcess_time().record(metric, TimeUnit.MILLISECONDS);

        return result;
    }


    /**
     * Отследить конец операции и получить ответ
     * @param joinPoint
     * @throws Throwable
     */
    @AfterReturning(value = "@annotation(ToRegistrReturnMethod)", returning = "response")
    @Order(2)
    public void catchEndOperation(JoinPoint joinPoint, ResponseEntity response) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String temp = "# Method End [" + methodSignature + "] \n ## Response: " + response.getBody();

        try(FileWriter writer = new FileWriter(logProcess, true)){
            writer.append(temp);
            writer.append("\n");
        }
    }
}
