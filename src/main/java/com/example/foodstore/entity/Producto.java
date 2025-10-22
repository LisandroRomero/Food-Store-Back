package com.example.foodstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "productos")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private double precio;

    @Builder.Default
    private boolean eliminado = false;
}
