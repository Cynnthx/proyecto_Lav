package org.example.lavanderia_proyecto.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_clientes")
    private Cliente cliente;

//    @OneToMany(mappedBy = "id_pedido", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List <PedidoPrendaCatalogo> pedidoPrendaCatalogo;

}