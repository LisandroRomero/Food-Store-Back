package com.example.foodstore.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoriaResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private int totalProductos; // Cantidad de productos en esta categoría
}
