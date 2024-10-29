package org.example.lavanderia_proyecto.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clientes", schema = "lavanderia", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellidos", nullable = false)
    private String apellidos;
    @Column(name = "direccion", nullable = false)
    private String direccion;
    @Column(name = "dni", nullable = false)
    private String dni;
    @Column(name = "telefono", nullable = false)
    private Integer telefono;
}
