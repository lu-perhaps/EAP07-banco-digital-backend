package com.udea.bancodigital.common.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tipo_estado")
@Data
public class TipoEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
}