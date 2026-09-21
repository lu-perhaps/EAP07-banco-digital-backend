package com.udea.bancodigital.repository;

import com.udea.bancodigital.entity.TipoTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoTransaccionRepository extends JpaRepository<TipoTransaccion, Long> {
    Optional<TipoTransaccion> findByNombreIgnoreCase(String nombre);
}
