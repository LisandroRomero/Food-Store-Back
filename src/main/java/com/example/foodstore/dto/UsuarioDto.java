package com.example.foodstore.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonInclude;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // El campo password no aparecerá cuando sea null
public class UsuarioDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String mail;
    private String rol;

    private String password; // Borrar acá -> Crear un Dto -> UsuarioLogin
}
