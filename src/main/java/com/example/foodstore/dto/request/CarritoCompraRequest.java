package com.example.foodstore.dto.request;

import lombok.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CarritoCompraRequest {

    @NotNull(message = "El usuario es obligatorio")
    @Positive(message = "El usuario debe ser un número positivo")
    private Long usuarioId;

    @Valid
    @NotNull(message = "Los items del carrito son obligatorios")
    private List<ItemCarritoRequest> items;
}
