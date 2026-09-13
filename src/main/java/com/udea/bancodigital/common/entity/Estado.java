package com.udea.bancodigital.Common.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estados")
@Data
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tipo_estado_id", nullable = false)
    private TipoEstado tipoEstado;

    @Column(nullable = false)
    private String nombre;
}