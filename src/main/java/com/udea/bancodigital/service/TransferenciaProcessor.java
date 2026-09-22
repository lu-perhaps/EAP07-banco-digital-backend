package com.udea.bancodigital.service;

import com.udea.bancodigital.entity.Cuenta;
import com.udea.bancodigital.entity.TipoTransaccion;
import com.udea.bancodigital.entity.Transaccion;
import com.udea.bancodigital.repository.CuentaRepository;
import com.udea.bancodigital.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class TransferenciaProcessor {

    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;

    @Transactional
    public Transaccion ejecutarTransferenciaExitosa(Long origenId, Long destinoId, BigDecimal monto, Long tipoId, String descripcion) {
        
        Cuenta origen = cuentaRepository.findById(origenId).orElseThrow();
        Cuenta destino = cuentaRepository.findById(destinoId).orElseThrow();
        TipoTransaccion tipo = tipoTransaccionRepository.findById(tipoId).orElseThrow();

        // Debitar
        origen.setSaldoDisponible(origen.getSaldoDisponible().subtract(monto));
        cuentaRepository.save(origen);

        // Acreditar
        destino.setSaldoDisponible(destino.getSaldoDisponible().add(monto));
        cuentaRepository.save(destino);

        // Registrar exitosa
        Transaccion tx = Transaccion.builder()
                .cuentaOrigen(origen)
                .cuentaDestino(destino)
                .monto(monto)
                .tipoTransaccion(tipo)
                .estado("EXITOSA")
                .fechaHora(OffsetDateTime.now())
                .descripcion(descripcion)
                .build();

        return transaccionRepository.save(tx);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarTransferenciaFallida(Long origenId, Long destinoId, BigDecimal monto, Long tipoId, String descripcion) {
        
        Cuenta origen = cuentaRepository.findById(origenId).orElseThrow();
        Cuenta destino = cuentaRepository.findById(destinoId).orElseThrow();
        TipoTransaccion tipo = tipoTransaccionRepository.findById(tipoId).orElseThrow();
        
        Transaccion tx = Transaccion.builder()
                .cuentaOrigen(origen)
                .cuentaDestino(destino)
                .monto(monto)
                .tipoTransaccion(tipo)
                .estado("FALLIDA")
                .fechaHora(OffsetDateTime.now())
                .descripcion(descripcion)
                .build();
        transaccionRepository.save(tx);
    }
}
