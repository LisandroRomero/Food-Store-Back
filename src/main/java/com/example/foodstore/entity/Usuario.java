package com.example.foodstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String mail;
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Builder.Default
    private boolean eliminado = false;
}
