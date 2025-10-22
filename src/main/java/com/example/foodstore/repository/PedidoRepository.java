package com.example.foodstore.repository;

import com.example.foodstore.entity.Pedido;
import com.example.foodstore.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findAllByEliminadoFalse();
    List<Pedido> findByUsuarioIdAndEliminadoFalse(Long usuarioId);
    List<Pedido> findByEstadoAndEliminadoFalse(Estado estado);
}
