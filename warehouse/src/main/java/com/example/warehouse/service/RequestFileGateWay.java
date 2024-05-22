package com.example.warehouse.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "requestInputChannel")
public interface RequestFileGateWay {
    void reportRequest(@Header(FileHeaders.FILENAME) String fileName, String response);
}
