package com.example.foodstore.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetallePedidoEdit {
    private int cantidad;
    private Double subtotal;
}
