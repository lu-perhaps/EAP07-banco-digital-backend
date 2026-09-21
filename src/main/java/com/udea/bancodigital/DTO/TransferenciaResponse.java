package com.udea.bancodigital.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class TransferenciaResponse {
    private Long transaccionId;
    private String numeroCuentaOrigen;
    private String numeroCuentaDestino;
    private BigDecimal monto;
    private String estado;
    private OffsetDateTime fechaHora;
}
