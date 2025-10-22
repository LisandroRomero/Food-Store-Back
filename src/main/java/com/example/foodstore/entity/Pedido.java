package com.example.foodstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Table(name = "pedidos")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private double total;

    // Relación unidireccional con Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Builder.Default
    private boolean eliminado = false;
}
