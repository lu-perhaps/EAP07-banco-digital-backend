package com.udea.bancodigital.transferencias.service;

import com.udea.bancodigital.transferencias.dto.TransferenciaRequest;
import com.udea.bancodigital.transferencias.dto.TransferenciaResponse;

public interface TransferenciaService {
    TransferenciaResponse realizarTransferencia(Long clienteId, TransferenciaRequest request);
}
