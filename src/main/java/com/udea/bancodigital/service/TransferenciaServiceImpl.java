package com.udea.bancodigital.service;

import com.udea.bancodigital.DTO.TransferenciaRequest;
import com.udea.bancodigital.DTO.TransferenciaResponse;
import com.udea.bancodigital.entity.Cuenta;
import com.udea.bancodigital.entity.TipoTransaccion;
import com.udea.bancodigital.entity.Transaccion;
import com.udea.bancodigital.repository.CuentaRepository;
import com.udea.bancodigital.repository.TipoTransaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferenciaServiceImpl implements TransferenciaService {

    private final CuentaRepository cuentaRepository;
    private final TipoTransaccionRepository tipoTransaccionRepository;
    private final TransferenciaProcessor transferenciaProcessor;

    @Override
    public TransferenciaResponse realizarTransferencia(Long clienteId, TransferenciaRequest request) {
        
        // 1. Validar que el monto sea > 0 (adicional a Jakarta Validation)
        if (request.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegocioException("INVALID_AMOUNT", "El monto de la transferencia debe ser mayor a cero", HttpStatus.BAD_REQUEST);
        }

        // 2. Misma cuenta origen y destino
        if (request.getNumeroCuentaOrigen().equals(request.getNumeroCuentaDestino())) {
            throw new NegocioException("SAME_ACCOUNT_TRANSFER", "No se puede transferir dinero a la misma cuenta de origen", HttpStatus.BAD_REQUEST);
        }

        // 3. Obtener cuenta origen
        Cuenta cuentaOrigen = cuentaRepository.findByNumeroCuenta(request.getNumeroCuentaOrigen())
                .orElseThrow(() -> new NegocioException("FORBIDDEN", "No tiene permisos sobre la cuenta de origen", HttpStatus.FORBIDDEN));

        // 4. Validar titularidad de cuenta origen
        if (!cuentaOrigen.getClienteId().equals(clienteId)) {
            throw new NegocioException("FORBIDDEN", "No tiene permisos sobre la cuenta de origen", HttpStatus.FORBIDDEN);
        }

        // 5. Obtener cuenta destino
        Cuenta cuentaDestino = cuentaRepository.findByNumeroCuenta(request.getNumeroCuentaDestino())
                .orElseThrow(() -> new NegocioException("DESTINATION_ACCOUNT_NOT_FOUND", "La cuenta de destino no existe", HttpStatus.NOT_FOUND));

        // 6. Validar estado de ambas cuentas (deben estar ACTIVAS)
        if (!"ACTIVA".equalsIgnoreCase(cuentaOrigen.getEstado().getNombre()) || 
            !"ACTIVA".equalsIgnoreCase(cuentaDestino.getEstado().getNombre())) {
            throw new NegocioException("ACCOUNT_NOT_ACTIVE", "Una o ambas cuentas no se encuentran activas", HttpStatus.CONFLICT);
        }

        // 7. Validar fondos suficientes
        if (cuentaOrigen.getSaldoDisponible().compareTo(request.getMonto()) < 0) {
            throw new NegocioException("INSUFFICIENT_FUNDS", "Saldo insuficiente en la cuenta de origen", HttpStatus.BAD_REQUEST);
        }

        // 8. Obtener el tipo de transacción TRANSFERENCIA
        TipoTransaccion tipo = tipoTransaccionRepository.findByNombreIgnoreCase("TRANSFERENCIA")
                .orElseThrow(() -> new NegocioException("INTERNAL_ERROR", "Tipo de transacción no configurado", HttpStatus.INTERNAL_SERVER_ERROR));

        // 9. Ejecutar transferencia con manejo de fallos
        Transaccion transaccionExitosa;
        try {
            transaccionExitosa = transferenciaProcessor.ejecutarTransferenciaExitosa(
                    cuentaOrigen.getId(), cuentaDestino.getId(), request.getMonto(), tipo.getId(), request.getDescripcion());
        } catch (Exception e) {
            // El débito y crédito sufren rollback automático por TransferenciaProcessor
            // Registramos la falla en una transacción nueva
            transferenciaProcessor.registrarTransferenciaFallida(
                    cuentaOrigen.getId(), cuentaDestino.getId(), request.getMonto(), tipo.getId(), request.getDescripcion());
            
            throw new NegocioException("INTERNAL_ERROR", "Fallo técnico durante la acreditación. La transacción ha sido revertida.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return TransferenciaResponse.builder()
                .transaccionId(transaccionExitosa.getId())
                .numeroCuentaOrigen(cuentaOrigen.getNumeroCuenta())
                .numeroCuentaDestino(cuentaDestino.getNumeroCuenta())
                .monto(transaccionExitosa.getMonto())
                .estado(transaccionExitosa.getEstado())
                .fechaHora(transaccionExitosa.getFechaHora())
                .build();
    }
}
