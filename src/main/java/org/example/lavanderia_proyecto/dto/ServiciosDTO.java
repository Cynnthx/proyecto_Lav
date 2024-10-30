package org.example.lavanderia_proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiciosDTO {
    private String nombre;
    private Double precio;
    private String tipoCatalogo;
    private String tipoPrenda;
}
