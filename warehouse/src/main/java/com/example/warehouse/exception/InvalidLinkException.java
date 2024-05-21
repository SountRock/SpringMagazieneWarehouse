package com.example.warehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Ошибка указания неверной ссылки на сервер магазина в конфигурационном файле
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidLinkException extends RuntimeException {
    public InvalidLinkException() {
        super("URI is not absolute");
    }
}
