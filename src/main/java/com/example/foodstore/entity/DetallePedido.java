package com.example.foodstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad;
    private Double subtotal;

    // Relación unidireccional con Pedido
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    // Relación unidireccional con Producto
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Builder.Default
    private boolean eliminado = false;
}
