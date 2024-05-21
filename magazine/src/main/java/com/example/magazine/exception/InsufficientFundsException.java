package com.example.magazine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.LOCKED)
public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException() {
        super("insufficient funds");
    }
}
