package com.example.warehouse.configuration;

import com.example.warehouse.aspect.ProcessMethodLoggerAspect;
import com.example.warehouse.aspect.TimeMethodLoggerAspect;
import com.example.warehouse.controller.MetricVariables;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = "com.example.warehouse")
@EnableAspectJAutoProxy
@EnableFeignClients(basePackages = "com.example.warehouse.proxy")
public class WarehouseConfig {
    @Bean
    public HttpHeaders httpHeaders(){
        return new HttpHeaders();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public TimeMethodLoggerAspect timeLogger(){
        return new TimeMethodLoggerAspect("LOG", "WarehouseLogTime.md");
    }

    @Bean
    public ProcessMethodLoggerAspect processLogger(){
        return new ProcessMethodLoggerAspect("LOG", "WarehouseLogProcess.md");
    }

    @Bean
    public HttpExchangeRepository httpTraceRepository() {
        return new InMemoryHttpExchangeRepository();
    }

    @Bean
    public MetricVariables metricVariables(){
        return new MetricVariables();
    }

}
