package com.example.foodstore.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PedidoEdit {
    private String estado; // Solo se puede editar el estado
    private double total;
}
