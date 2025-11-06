package com.example.foodstore.dto.response;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaSimpleDTO {
    private Long id;
    private String nombre;
}
