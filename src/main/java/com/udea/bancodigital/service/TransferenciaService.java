package com.udea.bancodigital.service;

import com.udea.bancodigital.DTO.TransferenciaRequest;
import com.udea.bancodigital.DTO.TransferenciaResponse;

public interface TransferenciaService {
    TransferenciaResponse realizarTransferencia(Long clienteId, TransferenciaRequest request);
}
