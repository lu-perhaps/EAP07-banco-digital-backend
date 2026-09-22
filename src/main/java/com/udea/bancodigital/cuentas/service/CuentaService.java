package com.udea.bancodigital.cuentas.service;

import com.udea.bancodigital.cuentas.dto.AperturaCuentaRequest;
import com.udea.bancodigital.cuentas.dto.CuentaResponse;

public interface CuentaService {
    CuentaResponse solicitarAperturaCuenta(Long clienteId, AperturaCuentaRequest request);
    CuentaResponse consultarSaldo(Long clienteId, String numeroCuenta);
}
