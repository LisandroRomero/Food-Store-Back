package com.example.foodstore.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductoCreate {
    private String nombre;
    private double precio;
}
