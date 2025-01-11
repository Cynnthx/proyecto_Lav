package org.example.lavanderia_proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.lavanderia_proyecto.modelos.Pedido;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private LocalDate fechaEntrega;
    private Double total;

    private String nombreCliente;
    private List <PedidoPrendaCatalogoDTO> servicios;

    public static PedidoDTO convertirPedidoAPedidoDTO(Pedido pedido) {
        if (pedido == null) return null;

        return PedidoDTO.builder()
                .total(pedido.getTotal())
                .fechaEntrega(pedido.getFecha())
                .nombreCliente(pedido.getCliente().getNombre())
                .servicios(PedidoPrendaCatalogoDTO.convertirListaPedidoPrendaCatalogoAPedidoPrendaCatalogoDTO(pedido.getPedidoPrendaCatalogo()))
                .build();
    }
}
