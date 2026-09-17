package com.udea.bancodigital.cuentas.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CuentasException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus status;

    public CuentasException(String errorCode, String message, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}