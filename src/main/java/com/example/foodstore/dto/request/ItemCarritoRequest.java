package com.example.foodstore.dto.request;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ItemCarritoRequest {

    @NotNull(message = "El producto es obligatorio")
    @Positive(message = "El producto debe ser un número positivo")
    private Long productoId;

    @Positive(message = "La cantidad debe ser un número positivo")
    private int cantidad;
}
