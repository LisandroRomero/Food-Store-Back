package com.example.foodstore.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PedidoEdit {
    
    private LocalDate fecha;
    
    @Pattern(regexp = "^(PENDIENTE|CONFIRMADO|CANCELADO|TERMINADO)$", message = "Estado inválido")
    private String estado;
    
    @PositiveOrZero(message = "El total debe ser cero o un número positivo")
    private Double total;
    
    // Campos de entrega (opcionales en edición)
    private String telefono;
    private String direccion;
    
    @Pattern(regexp = "^(efectivo|tarjeta|transferencia)$", message = "Método de pago inválido")
    private String metodoPago;
    
    private String notas;
}
