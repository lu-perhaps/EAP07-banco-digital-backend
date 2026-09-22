package com.udea.bancodigital.transferencias.repository;

import com.udea.bancodigital.transferencias.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
}
