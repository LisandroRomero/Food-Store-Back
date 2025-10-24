package com.example.foodstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "pedidos")
@Entity
@Getter
@Setter
@SuperBuilder
public class Pedido extends Base {

    @Column(nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private Double total;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles = new ArrayList<>();


    public void addDetalle(DetallePedido detalle) {
        if (detalle == null) return;
        if (!this.detalles.contains(detalle)) {
            this.detalles.add(detalle);
            detalle.setPedido(this);
        }
        return;
    }

    public void removeDetalle(DetallePedido detalle) {
        if (detalle == null) return;
        if (this.detalles.remove(detalle)) {
            detalle.setPedido(null);
        }
    }
}

