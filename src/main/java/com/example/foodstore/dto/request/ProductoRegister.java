package com.example.foodstore.dto.request;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductoRegister {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    private String descripcion;
    
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser un número positivo")
    private Double precio;
    
    @NotNull(message = "El stock es obligatorio")
    @PositiveOrZero(message = "El stock debe ser cero o un número positivo")
    private Integer stock;
    
    @Builder.Default
    private boolean disponible = true; // Por defecto disponible
    
    @Size(max = 255, message = "La URL de la imagen no puede superar los 255 caracteres")
    private String imagen;
    
    @NotNull(message = "La categoría es obligatoria")
    @Positive(message = "La categoría debe ser un número positivo")
    private Long categoriaId;
}
