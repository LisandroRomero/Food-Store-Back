package com.example.foodstore.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioCreate {
    private String nombre;
    private String apellido;
    private String mail;
    private String password;
    private String rol; // "USUARIO" o "ADMIN"
}
