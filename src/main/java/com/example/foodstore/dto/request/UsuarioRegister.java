package com.example.foodstore.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioRegister {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min=3,max = 50, message = "El apellido debe tener entre 3 y 50 caracteres")
    private String apellido;

    @Email(message = "El correo electrónico no es válido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
            message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un caracter especial")
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(regexp = "^(USUARIO|ADMIN)$", message = "Rol inválido")
    private String rol; // "USUARIO" o "ADMIN"
}
