package com.udea.bancodigital.repository;

import com.udea.bancodigital.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
    Optional<Estado> findByNombreIgnoreCase(String nombre);
}