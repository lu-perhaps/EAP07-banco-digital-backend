package com.udea.bancodigital.cuentas.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class CuentaResponse {
    private Long id;
    private Long clienteId;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoDisponible;
    private String estado;
    private OffsetDateTime fechaApertura;
}
