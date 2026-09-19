package com.udea.bancodigital.controller;

import com.udea.bancodigital.DTO.AperturaCuentaRequest;
import com.udea.bancodigital.DTO.CuentaResponse;
import com.udea.bancodigital.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @PostMapping
    public ResponseEntity<CuentaResponse> solicitarAperturaCuenta(
            @RequestHeader(value = "X-Cliente-Id", required = false) Long clienteId,
            @Valid @RequestBody AperturaCuentaRequest request) {

        CuentaResponse response = cuentaService.solicitarAperturaCuenta(clienteId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{numeroCuenta}/saldo")
    public ResponseEntity<CuentaResponse> consultarSaldo(
            @RequestHeader(value = "X-Cliente-Id", required = false) Long clienteId,
            @PathVariable String numeroCuenta) {

        CuentaResponse response = cuentaService.consultarSaldo(clienteId, numeroCuenta);
        return ResponseEntity.ok(response);
    }
}

