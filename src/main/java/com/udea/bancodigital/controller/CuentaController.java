package com.udea.bancodigital.controller;

import com.udea.bancodigital.DTO.AperturaCuentaRequest;
import com.udea.bancodigital.DTO.CuentaResponse;
import com.udea.bancodigital.service.CuentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cuentas")
@RequiredArgsConstructor
@Tag(name = "Cuentas", description = "Gestión de cuentas bancarias: apertura y consulta de saldo")
public class CuentaController {

    private final CuentaService cuentaService;

    @Operation(
            summary = "Solicitar apertura de cuenta (HU5)",
            description = """
                    Crea una nueva cuenta bancaria para el cliente autenticado.
                    
                    Tipos de cuenta válidos: `AHORROS` o `CORRIENTE`.
                    
                    El saldo inicial siempre es $0. Un cliente no puede tener dos cuentas activas del mismo tipo.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente.",
                    content = @Content(schema = @Schema(implementation = CuentaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Tipo de cuenta inválido o datos faltantes.", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado. Se requiere token JWT.", content = @Content),
            @ApiResponse(responseCode = "409", description = "El cliente ya posee una cuenta activa de ese tipo.", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CuentaResponse> solicitarAperturaCuenta(
            @Parameter(description = "ID del cliente autenticado", required = true)
            @RequestHeader(value = "X-Cliente-Id", required = false) Long clienteId,
            @Valid @RequestBody AperturaCuentaRequest request) {

        CuentaResponse response = cuentaService.solicitarAperturaCuenta(clienteId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Consultar saldo de una cuenta (HU6)",
            description = "Retorna el saldo disponible y el estado actual de la cuenta. Solo el dueño de la cuenta puede consultarla."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Saldo retornado exitosamente. Si la cuenta está BLOQUEADA, también se devuelve HTTP 200.",
                    content = @Content(schema = @Schema(implementation = CuentaResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado. Se requiere token JWT.", content = @Content),
            @ApiResponse(responseCode = "403", description = "La cuenta no pertenece al cliente autenticado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.", content = @Content)
    })
    @GetMapping("/{numeroCuenta}/saldo")
    public ResponseEntity<CuentaResponse> consultarSaldo(
            @Parameter(description = "ID del cliente autenticado", required = true)
            @RequestHeader(value = "X-Cliente-Id", required = false) Long clienteId,
            @Parameter(description = "Número de cuenta bancaria a consultar", example = "1234567890", required = true)
            @PathVariable String numeroCuenta) {

        CuentaResponse response = cuentaService.consultarSaldo(clienteId, numeroCuenta);
        return ResponseEntity.ok(response);
    }
}
