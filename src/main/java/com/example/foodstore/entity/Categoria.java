package com.example.foodstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.Entity;
import lombok.experimental.SuperBuilder;

@Table(name = "categorias")
@Entity
@Getter
@Setter
@SuperBuilder
public class Categoria extends Base{

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

}

    

