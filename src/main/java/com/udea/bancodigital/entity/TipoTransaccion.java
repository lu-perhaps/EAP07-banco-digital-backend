package com.udea.bancodigital.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipo_transaccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoTransaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre; // "TRANSFERENCIA", "DEPOSITO", "RETIRO", etc.
}
