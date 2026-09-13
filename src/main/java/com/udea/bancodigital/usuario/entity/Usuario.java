package com.udea.bancodigital.usuario.entity;

import com.udea.bancodigital.Common.entity.Estado;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "fecha_creacion", nullable = false)
    private java.time.LocalDateTime fechaCreacion;

    @Column(name = "cliente_id")
    private Long clienteId; // sin @ManyToOne todavía — ver nota abajo

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;
}