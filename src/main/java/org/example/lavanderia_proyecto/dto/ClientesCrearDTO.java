package org.example.lavanderia_proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientesCrearDTO {
    @NotBlank(message = "El nombre no puede ser vacio")
    private String nombre;
    @NotBlank(message = "El apellido no puede ser vacio")
    private String apellidos;
    @NotBlank(message = "La direccion no puede ser vacio")
    private String direccion;
    @NotBlank(message = "El DNI no puede ser vacio")
    private String dni;
    @Positive(message = "El telefono no puede ser vacio")
    private Integer telefono;
}
