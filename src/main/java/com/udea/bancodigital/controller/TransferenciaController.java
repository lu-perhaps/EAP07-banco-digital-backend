package com.udea.bancodigital.controller;

import com.udea.bancodigital.DTO.TransferenciaRequest;
import com.udea.bancodigital.DTO.TransferenciaResponse;
import com.udea.bancodigital.service.TransferenciaService;
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
@RequestMapping("/api/v1/transferencias")
@RequiredArgsConstructor
@Tag(name = "Transferencias", description = "Operaciones de transferencia de dinero entre cuentas (HU12)")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    @Operation(
            summary = "Realizar transferencia entre cuentas (HU12)",
            description = """
                    Transfiere dinero de una cuenta de origen a una cuenta de destino.
                    
                    **Validaciones aplicadas:**
                    - Monto debe ser mayor a $0 (`INVALID_AMOUNT`)
                    - Cuenta origen y destino deben ser distintas (`SAME_ACCOUNT_TRANSFER`)
                    - La cuenta de origen debe pertenecer al cliente autenticado (`FORBIDDEN`)
                    - La cuenta de destino debe existir (`DESTINATION_ACCOUNT_NOT_FOUND`)
                    - Ambas cuentas deben estar ACTIVAS (`ACCOUNT_NOT_ACTIVE`)
                    - La cuenta de origen debe tener saldo suficiente (`INSUFFICIENT_FUNDS`)
                    
                    En caso de fallo técnico durante la acreditación, el débito es revertido automáticamente y la transacción queda registrada como `FALLIDA`.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transferencia realizada exitosamente. Retorna el ID de la transacción.",
                    content = @Content(schema = @Schema(implementation = TransferenciaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Monto inválido (`INVALID_AMOUNT`), misma cuenta origen y destino (`SAME_ACCOUNT_TRANSFER`), o saldo insuficiente (`INSUFFICIENT_FUNDS`).",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "La cuenta de origen no pertenece al cliente autenticado (`FORBIDDEN`).",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "La cuenta de destino no existe (`DESTINATION_ACCOUNT_NOT_FOUND`).",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Una o ambas cuentas no están activas (`ACCOUNT_NOT_ACTIVE`).",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Fallo técnico durante la acreditación. La transacción fue revertida y registrada como FALLIDA.",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<TransferenciaResponse> realizarTransferencia(
            @Parameter(description = "ID del cliente autenticado", required = true)
            @RequestHeader(value = "X-Cliente-Id", required = false) Long clienteId,
            @Valid @RequestBody TransferenciaRequest request) {

        TransferenciaResponse response = transferenciaService.realizarTransferencia(clienteId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

