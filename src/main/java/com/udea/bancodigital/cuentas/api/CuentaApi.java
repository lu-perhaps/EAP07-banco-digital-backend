package com.udea.bancodigital.cuentas.api;

import java.math.BigDecimal;

/**
 * API pública del módulo de Cuentas.
 * <p>
 * Define las operaciones que otros módulos (ej. transferencias) pueden
 * realizar sobre las cuentas. Utiliza tipos de datos estándar o records/DTOs
 * para no exponer las entidades JPA.
 */
public interface CuentaApi {

    /**
     * DTO de información básica de la cuenta devuelta a otros módulos.
     */
    record CuentaInfo(
            Long id,
            Long clienteId,
            String numeroCuenta,
            BigDecimal saldoDisponible,
            String estadoNombre
    ) {}

    /**
     * Busca la información de una cuenta por su número.
     * @param numeroCuenta el número de cuenta
     * @return CuentaInfo si existe, de lo contrario lanza excepción de negocio
     */
    CuentaInfo obtenerInfoPorNumeroCuenta(String numeroCuenta);

    /**
     * Actualiza el saldo de una cuenta existente.
     * @param cuentaId ID interno de la cuenta
     * @param nuevoSaldo nuevo saldo a establecer
     */
    void actualizarSaldo(Long cuentaId, BigDecimal nuevoSaldo);
}
