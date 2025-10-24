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
    private double precio;
    private int stock;
    private boolean disponible;
    private CategoriaResponseDTO categoria;
}
