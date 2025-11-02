package com.example.foodstore.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductoEdit {

    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    private String descripcion;

    @Positive(message = "El precio debe ser un número positivo")
    private Double precio;

    @PositiveOrZero(message = "El stock debe ser cero o un número positivo")
    private Integer stock;

    private Boolean disponible;

    @Size(max = 255, message = "La URL de la imagen no puede superar los 255 caracteres")
    private String imagen;

    @Positive(message = "La categoría debe ser un número positivo")
    private Long categoriaId;
}
