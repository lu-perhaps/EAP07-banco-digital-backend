package com.udea.bancodigital.cuentas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AperturaCuentaRequest {

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    @Pattern(regexp = "^(?i)(AHORROS|CORRIENTE)$", message = "El tipo de cuenta debe ser AHORROS o CORRIENTE")
    private String tipoCuenta;

    public AperturaCuentaRequest() {
    }

    public AperturaCuentaRequest(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
}
