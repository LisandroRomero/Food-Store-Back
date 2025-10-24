package com.example.foodstore.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetallePedidoEdit {
    
    @Positive(message = "La cantidad debe ser un número positivo")
    private Integer cantidad;
    
    @Positive(message = "El subtotal debe ser un número positivo")
    private Double subtotal;
}
