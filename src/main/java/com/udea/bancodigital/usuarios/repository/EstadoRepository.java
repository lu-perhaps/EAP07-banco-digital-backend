package com.udea.bancodigital.usuarios.repository;

import com.udea.bancodigital.usuarios.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("usuariosEstadoRepository")
public interface EstadoRepository extends JpaRepository<Estado, Long> {
    Optional<Estado> findByNombreIgnoreCase(String nombre);
}
