package com.example.foodstore.repository;

import com.example.foodstore.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByActivoTrue();
    List<Producto> findByCategoriaIdAndDisponibleTrueAndStockGreaterThan(Long categoriaId, int stock);
}
