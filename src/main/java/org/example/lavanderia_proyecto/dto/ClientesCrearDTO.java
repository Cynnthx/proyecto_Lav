package org.example.lavanderia_proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientesCrearDTO {
    private String nombre;
    private String apellidos;
    private String direccion;
    private String dni;
    private Integer telefono;
}
