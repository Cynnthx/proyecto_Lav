package org.example.lavanderia_proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrearPedidoDTO {

    private LocalDate fechaEntrega;
    private Double total;

    private Integer IdCliente;

}
