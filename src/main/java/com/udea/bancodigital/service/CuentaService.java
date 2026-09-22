package com.udea.bancodigital.service;

import com.udea.bancodigital.DTO.AperturaCuentaRequest;
import com.udea.bancodigital.DTO.CuentaResponse;

public interface CuentaService {
    CuentaResponse solicitarAperturaCuenta(Long clienteId, AperturaCuentaRequest request);
    CuentaResponse consultarSaldo(Long clienteId, String numeroCuenta);
}

