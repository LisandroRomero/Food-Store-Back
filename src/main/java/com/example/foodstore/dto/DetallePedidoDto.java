package com.example.foodstore.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetallePedidoDto {
    private Long id;
    private int cantidad;
    private Double subtotal;
    private PedidoDto pedido;      // DTO del pedido completo
    private ProductoDto producto;  // DTO del producto completo
}
