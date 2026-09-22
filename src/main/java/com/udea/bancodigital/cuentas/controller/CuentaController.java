package com.udea.bancodigital.cuentas.controller;

import com.udea.bancodigital.cuentas.dto.AperturaCuentaRequest;
import com.udea.bancodigital.cuentas.dto.CuentaResponse;
import com.udea.bancodigital.cuentas.service.CuentaService;
import com.udea.bancodigital.usuarios.api.UsuarioApi;
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
    // Única dependencia externa: solo se acopla a la interfaz pública (API) del módulo usuarios
    private final UsuarioApi usuarioApi;

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
            @Valid @RequestBody AperturaCuentaRequest request) {

        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        Long clienteId = usuarioApi.obtenerIdClientePorEmail(email);

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
            @Parameter(description = "Número de cuenta bancaria a consultar", example = "1234567890", required = true)
            @PathVariable String numeroCuenta) {

        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        Long clienteId = usuarioApi.obtenerIdClientePorEmail(email);

        CuentaResponse response = cuentaService.consultarSaldo(clienteId, numeroCuenta);
        return ResponseEntity.ok(response);
    }
}
