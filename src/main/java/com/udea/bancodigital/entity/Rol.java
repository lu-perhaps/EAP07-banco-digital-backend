package com.udea.bancodigital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    // @Data genera equals/hashCode sobre todos los campos, lo que rompe con
    // entidades JPA: dos instancias de la misma fila dejan de ser iguales en
    // cuanto una carga perezosa cambia cualquier atributo. Solo cuenta el id.
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (!(objeto instanceof Rol otro)) {
            return false;
        }
        return id != null && id.equals(otro.id);
    }

    // Constante a proposito: el id es null antes de persistir, asi que hashearlo
    // sacaria a la entidad de cualquier HashSet donde ya estuviera.
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Rol{id=" + id + ", nombre=" + nombre + "}";
    }
}
