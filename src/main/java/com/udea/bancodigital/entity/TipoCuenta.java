package com.udea.bancodigital.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipo_cuenta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre; // "AHORROS", "CORRIENTE"
}

