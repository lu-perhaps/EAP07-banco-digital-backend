package com.udea.bancodigital.cuentas.entity;

import com.udea.bancodigital.shared.entity.Estado;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "cuentas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Se usa Long para desacoplar del módulo clientes mientras se finaliza la integración
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "numero_cuenta", nullable = false, unique = true, length = 20)
    private String numeroCuenta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_cuenta_id", nullable = false)
    private TipoCuenta tipoCuenta;

    @Column(name = "saldo_disponible", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoDisponible;

    // Estado viene de cuentas.entity.Estado (interno al módulo)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

    @Column(name = "fecha_apertura", nullable = false)
    private OffsetDateTime fechaApertura;
}
