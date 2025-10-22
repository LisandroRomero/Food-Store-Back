package com.example.foodstore.dto;

import lombok.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PedidoDto {
    private Long id;
    private LocalDate fecha;
    private String estado;
    private double total;
    private UsuarioDto usuario; // DTO del usuario completo
}
