package org.example.lavanderia_proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagosDTO {
    private Integer idPedido;
    private Double totalPagado;
    private Boolean pagado;
    private Double saldoPendiente;
}
