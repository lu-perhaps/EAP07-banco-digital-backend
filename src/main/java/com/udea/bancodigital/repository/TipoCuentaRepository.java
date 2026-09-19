package com.udea.bancodigital.repository;

import com.udea.bancodigital.entity.TipoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TipoCuentaRepository extends JpaRepository<TipoCuenta, Long> {
    Optional<TipoCuenta> findByNombreIgnoreCase(String nombre);
}

