package org.example.lavanderia_proyecto.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Entity
@Table(name = "pedido_prenda_catalogo", schema = "lavanderia")
//, catalog = "postgres"
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

    @NotNull(message = "El precio no puede ser nulo")
    @Column(name = "precio")
    private Double precio;

    @Min(value = 0, message = "La cantidad debe ser mayor o igual a 0")
    @Column(name = "cantidad")
    private Integer cantidad;

    @NotNull(message = "El pedido no puede ser nulo")
    @ManyToOne (cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @NotNull(message = "La prenda no puede ser nula")
    @ManyToOne  (cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    @JoinColumn(name = "id_prenda")
    private Prenda prenda;

    @NotNull(message = "El catálogo no puede ser nulo")
    @ManyToOne  (cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    @JoinColumn(name = "id_catalogo")
    private Catalogo catalogo;
}
