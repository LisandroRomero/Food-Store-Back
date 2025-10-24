package com.example.foodstore.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetallePedidoRegister {
    
    @Positive(message = "La cantidad debe ser un número positivo")
    private int cantidad;
    
    @NotNull(message = "El subtotal es obligatorio")
    @Positive(message = "El subtotal debe ser un número positivo")
    private Double subtotal;
    
    @NotNull(message = "El pedido es obligatorio")
    @Positive(message = "El pedido debe ser un número positivo")
    private Long pedidoId;    // ID del pedido relacionado
    
    @NotNull(message = "El producto es obligatorio")
    @Positive(message = "El producto debe ser un número positivo")
    private Long productoId;  // ID del producto relacionado
}
