package com.example.foodstore.dto;

import lombok.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PedidoCreate {
    private LocalDate fecha;
    private String estado; // "PENDIENTE", "CONFIRMADO", etc.
    private double total;
    private Long usuarioId; // ID del usuario relacionado
}
