package com.example.magazine.controller;

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
     * Получить колическов успешных покупок в магазине
     * @return
     */
    @GetMapping("/count_put")
    public ResponseEntity count_put(){
        TreeMap<String, Double> response = new TreeMap<>();
        response.put("count_put_buy", metricVariables.getCount_put_request().count());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Получить колическов покупок в магазине
     * @return
     */
    @GetMapping("/count_try_put")
    public ResponseEntity count_try_put(){
        TreeMap<String, Double> response = new TreeMap<>();
        response.put("count_try_put_buy", metricVariables.getCount_try_put_request().count());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Получить среднее время выполнения запроса покупки продукта
     * @return
     */
    @GetMapping("/process_time")
    public ResponseEntity process_time(){
        TreeMap<String, Double> response = new TreeMap<>();
        response.put("process_time_buy", metricVariables.getProcess_time().mean(TimeUnit.MILLISECONDS));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
