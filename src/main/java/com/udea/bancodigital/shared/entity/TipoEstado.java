package com.udea.bancodigital.shared.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad compartida del catálogo de tipos de estado.
 * Reside en `shared` porque tanto el módulo `usuarios` como `cuentas`
 * referencian la misma tabla `tipo_estado` de la base de datos.
 */
@Entity
@Table(name = "tipo_estado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (!(objeto instanceof TipoEstado otro)) return false;
        return id != null && id.equals(otro.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "TipoEstado{id=" + id + ", nombre=" + nombre + "}";
    }
}
