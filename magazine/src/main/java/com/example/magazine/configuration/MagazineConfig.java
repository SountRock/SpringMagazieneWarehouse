package com.example.magazine.configuration;

import com.example.magazine.aspect.ProcessMethodLoggerAspect;
import com.example.magazine.aspect.TimeMethodLoggerAspect;
import com.example.magazine.controller.MetricVariables;
import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
@ComponentScan(basePackages = "com.example.magazine")
@EnableAspectJAutoProxy
public class MagazineConfig {
    private static long countRequest = 1;


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
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("magazine/UserRequest"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAutoCreateDirectory(true);;

        return handler;
    }

}
