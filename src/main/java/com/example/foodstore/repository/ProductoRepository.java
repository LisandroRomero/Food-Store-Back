package com.example.foodstore.repository;

import com.example.foodstore.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Busca un producto por nombre (case insensitive)
     */
    Optional<Producto> findByNombreIgnoreCase(String nombre);

    /**
     * Verifica si existe un producto con el nombre dado (excluyendo el ID especificado)
     */
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

    /**
     * Verifica si existe un producto con el nombre dado
     */
    boolean existsByNombreIgnoreCase(String nombre);

    /**
     * Busca productos por categoría ID
     */
    List<Producto> findByCategoriaId(Long categoriaId);

    /**
     * Busca productos disponibles
     */
    List<Producto> findByDisponibleTrue();

    /**
     * Busca productos disponibles por categoría
     */
    List<Producto> findByCategoriaIdAndDisponibleTrue(Long categoriaId);

    /**
     * Busca productos que contengan el texto en el nombre o descripción
     */
    @Query("SELECT p FROM Producto p WHERE " +
           "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
           "LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Producto> findByTextoContaining(@Param("texto") String texto);

    /**
     * Busca productos disponibles que contengan el texto en el nombre o descripción
     */
    @Query("SELECT p FROM Producto p WHERE p.disponible = true AND " +
           "(LOWER(p.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
           "LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :texto, '%')))")
    List<Producto> findByTextoContainingAndDisponibleTrue(@Param("texto") String texto);

    /**
     * Busca productos con stock bajo (menor al mínimo especificado)
     */
    @Query("SELECT p FROM Producto p WHERE p.stock < :stockMinimo")
    List<Producto> findProductosConStockBajo(@Param("stockMinimo") int stockMinimo);

    /**
     * Busca productos por rango de precio
     */
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :precioMin AND :precioMax")
    List<Producto> findByPrecioBetween(@Param("precioMin") double precioMin, @Param("precioMax") double precioMax);

    /**
     * Busca productos disponibles por rango de precio
     */
    @Query("SELECT p FROM Producto p WHERE p.disponible = true AND p.precio BETWEEN :precioMin AND :precioMax")
    List<Producto> findByPrecioBetweenAndDisponibleTrue(@Param("precioMin") double precioMin, @Param("precioMax") double precioMax);
}