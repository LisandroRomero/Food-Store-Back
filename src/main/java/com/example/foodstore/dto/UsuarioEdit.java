package com.example.foodstore.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioEdit {
    private String nombre;
    private String apellido;
    private String password;
}
