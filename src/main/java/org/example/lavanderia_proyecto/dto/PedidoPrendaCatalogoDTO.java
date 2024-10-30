package org.example.lavanderia_proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.lavanderia_proyecto.modelos.PedidoPrendaCatalogo;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPrendaCatalogoDTO {
    private String nombrePrenda;
    private Double precio;
    private Integer cantidad;
    private String nombreServicio;

    public static List<PedidoPrendaCatalogoDTO> convertirListaPedidoPrendaCatalogoAPedidoPrendaCatalogoDTO(List<PedidoPrendaCatalogo> pedidoPrendaCatalogoList) {
        return pedidoPrendaCatalogoList.stream()
                .map(pedidoPrendaCatalogo -> PedidoPrendaCatalogoDTO.builder()
                        .nombrePrenda(pedidoPrendaCatalogo.getPrenda().getNombre())
                        .precio(pedidoPrendaCatalogo.getPrecio())
                        .cantidad(pedidoPrendaCatalogo.getCantidad())
                        .nombreServicio(pedidoPrendaCatalogo.getCatalogo().getTipoCatalogo().name())
                        .build())
                .collect(Collectors.toList());
    }

    public static PedidoPrendaCatalogoDTO convertirPedidoPrendaCatalogoAPedidoPrendaCatalogoDTO(PedidoPrendaCatalogo pedidoPrendaCatalogo) {
        return PedidoPrendaCatalogoDTO.builder()
                .nombrePrenda(pedidoPrendaCatalogo.getPrenda().getNombre())
                .precio(pedidoPrendaCatalogo.getPrecio())
                .cantidad(pedidoPrendaCatalogo.getCantidad())
                .nombreServicio(pedidoPrendaCatalogo.getCatalogo().getTipoCatalogo().name())
                .build();
    }
}
