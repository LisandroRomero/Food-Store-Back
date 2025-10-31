package com.example.foodstore.dto.request;

import lombok.*;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PedidoRegister {
    
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
    
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(PENDIENTE|CONFIRMADO|CANCELADO|TERMINADO)$", message = "Estado inválido")
    private String estado;
    
    @PositiveOrZero(message = "El total debe ser cero o un número positivo")
    private double total;
    
    @NotNull(message = "El usuario es obligatorio")
    @PositiveOrZero(message = "El usuario debe ser un número positivo")
    private Long usuarioId;
}
