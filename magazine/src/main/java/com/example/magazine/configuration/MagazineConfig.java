package com.example.magazine.configuration;

import com.example.magazine.aspect.ProcessMethodLoggerAspect;
import com.example.magazine.aspect.TimeMethodLoggerAspect;
import com.example.magazine.controller.MetricVariables;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;

@Configuration
@ComponentScan(basePackages = "com.example.magazine")
@EnableAspectJAutoProxy
public class MagazineConfig {
    @Bean
    public TimeMethodLoggerAspect timeLogger(){
        return new TimeMethodLoggerAspect("LOG", "MagazineLogTime.md");
    }

    @Bean
    public ProcessMethodLoggerAspect processLogger(){
        return new ProcessMethodLoggerAspect("LOG", "MagazineLogProcess.md");
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
