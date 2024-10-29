package org.example.lavanderia_proyecto.modelos;

import jakarta.persistence.*;
import lombok.*;
import org.example.lavanderia_proyecto.enumerados.TipoCatalogo;
import org.example.lavanderia_proyecto.enumerados.TipoPrenda;

@Entity
@Table(name = "catalogo", schema = "lavanderia", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Catalogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;


    @Column(name="precio_servicio_prenda")
    private Double precioServPrenda;

    @Column(name="tipo_prenda")
    @Enumerated(EnumType.ORDINAL)
    private TipoPrenda tipoPrenda;

    @Column(name="tipo_servicio")
    @Enumerated(EnumType.ORDINAL)
    private TipoCatalogo tipoCatalogo;
}
