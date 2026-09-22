package com.udea.bancodigital.controller;

import com.udea.bancodigital.DTO.TransferenciaRequest;
import com.udea.bancodigital.DTO.TransferenciaResponse;
import com.udea.bancodigital.service.TransferenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transferencias")
@RequiredArgsConstructor
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    @PostMapping
    public ResponseEntity<TransferenciaResponse> realizarTransferencia(
            @RequestHeader(value = "X-Cliente-Id", required = false) Long clienteId,
            @Valid @RequestBody TransferenciaRequest request) {

        // Nota: en un escenario 100% integrado con Spring Security, el clienteId
        // podría extraerse directamente del SecurityContext (el JWT token).
        // Por compatibilidad con la rama actual, seguimos el patrón de los otros controladores
        // que lo reciben vía cabecera temporalmente.
        TransferenciaResponse response = transferenciaService.realizarTransferencia(clienteId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
