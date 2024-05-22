package com.example.magazine.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "requestInputChannel")
//@MessagingGateway(defaultReplyChannel = "requestInputChannel") //Для вызова ошибки
public interface RequestFileGateWay {
    void reportRequest(@Header(FileHeaders.FILENAME) String fileName, String response);
}
