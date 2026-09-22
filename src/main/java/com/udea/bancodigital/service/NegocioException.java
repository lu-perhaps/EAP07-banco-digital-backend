package com.udea.bancodigital.service;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NegocioException extends RuntimeException {

    private final String errorCode;
    private final HttpStatus status;

    public NegocioException(String errorCode, String message, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
