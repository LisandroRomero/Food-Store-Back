package com.example.foodstore.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private boolean disponible;
    private boolean activo;
    private CategoriaResponseDTO categoria;
}
