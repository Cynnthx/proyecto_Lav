package org.example.lavanderia_proyecto.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prenda", schema = "lavanderia")
//, catalog = "postgres"
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Prenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;

}
