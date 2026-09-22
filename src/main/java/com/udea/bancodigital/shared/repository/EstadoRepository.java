package com.udea.bancodigital.shared.repository;

import com.udea.bancodigital.shared.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio compartido para la entidad Estado.
 * Centralizado en `shared` para evitar conflictos de bean JPA
 * entre los módulos `usuarios` y `cuentas`.
 */
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
    Optional<Estado> findByNombreIgnoreCase(String nombre);
}
