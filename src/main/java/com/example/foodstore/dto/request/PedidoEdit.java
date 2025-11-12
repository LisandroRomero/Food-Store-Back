package com.example.foodstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PedidoEdit {

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(PENDIENTE|CONFIRMADO|CANCELADO|TERMINADO)$", message = "Estado inválido")
    private String estado;

}
