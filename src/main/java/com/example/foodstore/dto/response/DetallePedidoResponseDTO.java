package com.example.foodstore.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetallePedidoResponseDTO {
    private Long id;
    private int cantidad;
    private Double subtotal;
    private ProductoResponseDTO producto;  // DTO del producto completo
}
