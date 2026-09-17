package com.udea.bancodigital.cuentas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AperturaCuentaRequest {
    @NotBlank(message = "El tipo de cuenta es obligatorio")
    private String tipoCuenta; // "AHORROS" o "CORRIENTE"
}