package org.example.lavanderia_proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.lavanderia_proyecto.enumerados.TipoCatalogo;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrearPedidoDTO {
    private LocalDate fechaEntrega;
    private Double total;
    private Integer IdCliente;
    private Integer IdPrenda;
    private String descripcion;

    private List<TipoCatalogo> servicios;


}
