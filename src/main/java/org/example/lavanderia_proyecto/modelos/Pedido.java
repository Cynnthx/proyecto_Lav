package org.example.lavanderia_proyecto.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido", schema = "lavanderia", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fecha_entrega", nullable = false)
    private LocalDate fecha;
    @Column(name="total", nullable = false)
    private Double total;
    @ManyToOne()
    @JoinColumn(name = "id_clientes")
    private Cliente cliente;

   @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
       private List<PedidoPrendaCatalogo> pedidoPrendaCatalogo = new ArrayList<>();

}
