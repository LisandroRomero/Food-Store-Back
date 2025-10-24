package com.example.foodstore.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetallePedidoCreate {
    private int cantidad;
    private Double subtotal;
    private Long pedidoId;    // ID del pedido relacionado
    private Long productoId;  // ID del producto relacionado
}
