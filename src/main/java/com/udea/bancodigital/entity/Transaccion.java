package com.udea.bancodigital.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "transacciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cuenta de origen del dinero
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_origen_id", nullable = false)
    private Cuenta cuentaOrigen;

    // Cuenta de destino del dinero
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_destino_id", nullable = false)
    private Cuenta cuentaDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_transaccion_id", nullable = false)
    private TipoTransaccion tipoTransaccion;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal monto;

    // "EXITOSA" | "FALLIDA" — se persiste siempre para trazabilidad
    @Column(nullable = false, length = 20)
    private String estado;

    // OffsetDateTime cumple la convención del equipo: TIMESTAMPTZ en PostgreSQL
    @Column(name = "fecha_hora", nullable = false)
    private OffsetDateTime fechaHora;

    @Column(length = 255)
    private String descripcion;

    // equals/hashCode correctos para entidades JPA — solo cuenta el id
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (!(objeto instanceof Transaccion otro)) return false;
        return id != null && id.equals(otro.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Transaccion{id=" + id + ", estado=" + estado + ", monto=" + monto + "}";
    }
}
