package com.udea.bancodigital.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferenciaRequest {

    @NotBlank(message = "El número de cuenta de origen es obligatorio")
    private String numeroCuentaOrigen;

    @NotBlank(message = "El número de cuenta de destino es obligatorio")
    private String numeroCuentaDestino;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto de la transferencia debe ser mayor que cero")
    private BigDecimal monto;

    private String descripcion;
}
