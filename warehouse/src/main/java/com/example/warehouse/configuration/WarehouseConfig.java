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
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Configuration
@ComponentScan(basePackages = "com.example.warehouse")
@EnableAspectJAutoProxy
@EnableFeignClients(basePackages = "com.example.warehouse.proxy")
public class WarehouseConfig {
    private static long countRequest = 1;


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

    /**
     * Каннал ввода
     * @return
     */
    @Bean
    public MessageChannel requestInputChannel(){
        return new DirectChannel();
    }

    /**
     * Канал записи
     * @return
     */
    @Bean
    public MessageChannel requestOutputChannel(){
        return new DirectChannel();
    }

    /**
     * Трансформер ответа запроса в запись в файл
     * @return
     */
    @Bean
    @Transformer(inputChannel = "requestInputChannel", outputChannel = "requestOutputChannel")
    public GenericTransformer<String, String> transformer(){
        return text -> {
            return "\nrequest" + countRequest++ + ": " + text + '\n';
        };
    }

    /**
     * Отловщик сообщений для записи в файл
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "requestOutputChannel")
    public FileWritingMessageHandler handler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("warehouse/UserRequest"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAutoCreateDirectory(true);;

        return handler;
    }

}
