package com.example.foodstore.service;

import com.example.foodstore.dto.request.ProductoRegister;
import com.example.foodstore.dto.request.ProductoEdit;
import com.example.foodstore.dto.response.ProductoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductoService {

    /**
     * Crea un nuevo producto
     */
    ProductoResponseDTO crear(ProductoRegister productoRegister);

    /**
     * Busca un producto por ID
     */
    ProductoResponseDTO buscarPorId(Long id);

    /**
     * Busca todos los productos (para admin)
     */
    List<ProductoResponseDTO> buscarTodos();

    /**
     * Busca productos con paginación
     */
    Page<ProductoResponseDTO> buscarConPaginacion(Pageable pageable);

    /**
     * Busca productos por categoría
     */
    List<ProductoResponseDTO> buscarPorCategoria(Long categoriaId);

    /**
     * Busca productos disponibles (para la tienda)
     */
    List<ProductoResponseDTO> buscarDisponibles();

    /**
     * Actualiza un producto existente
     */
    ProductoResponseDTO actualizar(Long id, ProductoEdit productoEdit);

    /**
     * Elimina un producto por ID
     */
    void eliminar(Long id);


    void restaurar(Long id);

    /**
     * Cambia la disponibilidad de un producto
     */
    ProductoResponseDTO cambiarDisponibilidad(Long id, boolean disponible);
}