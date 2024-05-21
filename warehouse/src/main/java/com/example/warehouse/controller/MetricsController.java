package com.example.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * Контроллер для получения конкретных метрик в ввиде json
 */
@RestController
@RequestMapping("/myMetrics")
public class MetricsController {
    @Autowired
    private MetricVariables metricVariables;

    /**
     * Получить колическов успешных отправок продуктов в магазин
     * @return
     */
    @GetMapping("/count_put")
    public ResponseEntity count_put(){
        TreeMap<String, Double> response = new TreeMap<>();
        response.put("count_put_push", metricVariables.getCount_put_request().count());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Получить колическов отправок продуктов в магазин
     * @return
     */
    @GetMapping("/count_try_put")
    public ResponseEntity count_try_put(){
        TreeMap<String, Double> response = new TreeMap<>();
        response.put("count_try_put_push", metricVariables.getCount_try_put_request().count());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Получить среднее время выполнения запроса отправки продуктов в магазин
     * @return
     */
    @GetMapping("/process_time")
    public ResponseEntity process_time(){
        TreeMap<String, Double> response = new TreeMap<>();
        response.put("process_time_push", metricVariables.getProcess_time().mean(TimeUnit.MILLISECONDS));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

