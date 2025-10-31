package com.example.foodstore.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PedidoResponseDTO {
    private Long id;
    private LocalDate fecha;
    private String estado;
    private double total;
    private UsuarioResponseDTO usuario;
    @Builder.Default
    private List<DetallePedidoResponseDTO> detalles = new ArrayList<>(); 
}
