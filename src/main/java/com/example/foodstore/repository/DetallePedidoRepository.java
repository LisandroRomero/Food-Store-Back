package com.example.foodstore.repository;

import com.example.foodstore.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    List<DetallePedido> findAllByEliminadoFalse();
    List<DetallePedido> findByPedidoIdAndEliminadoFalse(Long pedidoId);
}
