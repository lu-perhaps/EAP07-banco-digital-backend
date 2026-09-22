package com.udea.bancodigital.shared.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad compartida del catálogo de estados.
 * Reside en `shared` porque tanto el módulo `usuarios` como `cuentas`
 * referencian la misma tabla `estados` de la base de datos.
 */
@Entity
@Table(name = "estados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_estado_id", nullable = false)
    private TipoEstado tipoEstado;

    @Column(nullable = false)
    private String nombre;

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (!(objeto instanceof Estado otro)) return false;
        return id != null && id.equals(otro.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Estado{id=" + id + ", nombre=" + nombre + "}";
    }
}
