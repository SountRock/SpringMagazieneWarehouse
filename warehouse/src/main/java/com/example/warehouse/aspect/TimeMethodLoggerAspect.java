package com.example.warehouse.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.io.File;
import java.io.FileWriter;

/**
 * Аспект отслеживания времени выполнения всех методов сервера
 */
@Aspect
public class TimeMethodLoggerAspect {
    private File logTime;

    public TimeMethodLoggerAspect(String parentDirectory, String timeLoggerFileName) {
        logTime = new File(parentDirectory);
        logTime.mkdirs();
        logTime = new File(parentDirectory, timeLoggerFileName);
    }

    //!!! Примечание: execution уступает в универсальности Методу с созданием своих аннотаций
    /**
     * Отследить время выполнения
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.example.warehouse.service.*.*(..))")
    public Object timeMetric(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();

        long metric = end - start;

        try(FileWriter writer = new FileWriter(logTime, true)){
            writer.append("# Method: [" + methodSignature + "] \n ## Was executed for "  + metric + " millisecond");
            writer.append("\n");
        }

        return result;
    }
}
