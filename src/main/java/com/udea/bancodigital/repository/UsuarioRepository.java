package com.udea.bancodigital.repository;

import com.udea.bancodigital.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
// Este repositorio va a trabajar con la entidad Usuario y su ID es de tipo Long
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}