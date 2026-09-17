package com.udea.bancodigital.cuentas.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    private String errorCode;
    private String message;
    private String details;
    private String traceId;
    private LocalDateTime timestamp;
}