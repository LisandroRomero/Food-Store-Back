package com.example.foodstore.dto.response;

import lombok.*;
import java.util.List;

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
    private int totalProductos;
    private boolean activo;

    // Información de la categoría padre
    private CategoriaSimpleDTO categoriaPadre;

    // Lista de subcategorías
    private List<CategoriaSimpleDTO> subcategorias;

    // Contador de subcategorías
    private int totalSubcategorias;
}
