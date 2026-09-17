package com.udea.bancodigital.cuentas.controller;

import com.udea.bancodigital.cuentas.dto.ErrorResponse;
import com.udea.bancodigital.cuentas.exception.CuentasException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice(basePackages = "com.udea.bancodigital.cuentas")
public class CuentasExceptionHandler {

    @ExceptionHandler(CuentasException.class)
    public ResponseEntity<ErrorResponse> handleCuentasException(CuentasException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .details(ex.getLocalizedMessage())
                .traceId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(ex.getStatus()).body(error);
    }
}