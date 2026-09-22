package com.udea.bancodigital.cuentas.repository;

import com.udea.bancodigital.cuentas.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("cuentasEstadoRepository")
public interface EstadoRepository extends JpaRepository<Estado, Long> {
    Optional<Estado> findByNombreIgnoreCase(String nombre);
}
