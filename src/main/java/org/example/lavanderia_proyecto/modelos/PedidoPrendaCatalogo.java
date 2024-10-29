package org.example.lavanderia_proyecto.modelos;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "pedido_prenda_catalogo", schema = "lavanderia", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PedidoPrendaCatalogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "precio")
    private Integer precio;
    @Column(name = "cantidad")
    private Integer cantidad;

    @ManyToOne (cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
    @ManyToOne  (cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    @JoinColumn(name = "id_prenda")
    private Prenda prenda;
    @ManyToOne  (cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    @JoinColumn(name = "id_catalogo")
    private Catalogo catalogo;
}
