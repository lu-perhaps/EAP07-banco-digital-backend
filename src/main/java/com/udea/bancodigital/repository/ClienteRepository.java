package com.udea.bancodigital.repository;

import com.udea.bancodigital.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
