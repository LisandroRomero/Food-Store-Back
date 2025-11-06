package com.example.foodstore.repository;

import com.example.foodstore.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    /**
     * Busca una categoría por nombre (case insensitive)
     */
    Optional<Categoria> findByNombreIgnoreCase(String nombre);

    /**
     * Verifica si existe una categoría con el nombre dado (excluyendo el ID especificado)
     */
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

    /**
     * Verifica si existe una categoría con el nombre dado
     */
    boolean existsByNombreIgnoreCase(String nombre);

    /**
     * Busca categorías que contengan el texto en el nombre o descripción
     */
    @Query("SELECT c FROM Categoria c WHERE " +
           "LOWER(c.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
           "LOWER(c.descripcion) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Categoria> findByTextoContaining(@Param("texto") String texto);

    /**
     * Obtiene categorías con al menos un producto disponible
     */
    @Query("SELECT DISTINCT c FROM Categoria c " +
           "JOIN c.productos p WHERE p.disponible = true")
    List<Categoria> findCategoriasConProductosDisponibles();

    // Buscar categorías raíz (sin padre)
    List<Categoria> findByCategoriaPadreIsNull();

    // Buscar categorías raíz activas
    List<Categoria> findByCategoriaPadreIsNullAndActivoTrue();

    // Buscar subcategorías por padre
    List<Categoria> findByCategoriaPadreId(Long categoriaPadreId);

    // Buscar subcategorías activas por padre
    List<Categoria> findByCategoriaPadreIdAndActivoTrue(Long categoriaPadreId);

    // Buscar categorías por nivel (opcional, para futuras expansiones)
    @Query("SELECT c FROM Categoria c WHERE c.categoriaPadre IS NULL AND c.activo = true")
    List<Categoria> findCategoriasRaizActivas();
}