package org.example.lavanderia_proyecto.modelos;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "pagos", schema = "lavanderia", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class Pagos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "pagado")
    private Boolean pagado;
    @Column(name = "saldo_pendiente")
    private Double saldoPendiente;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
    @ManyToOne
    @JoinColumn(name = "id_clientes")
    private Cliente cliente;

}
