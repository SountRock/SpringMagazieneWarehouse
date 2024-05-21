package com.example.warehouse.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import lombok.Data;

/**
 * Класс для хранения переменных своих метрик
 */
@Data
public class MetricVariables {
    private final Timer process_time = Metrics.timer("process_time_my");
    private final Counter count_put_request = Metrics.counter("count_put_request");
    private final Counter count_try_put_request  = Metrics.counter("count_try_put_request");
}
