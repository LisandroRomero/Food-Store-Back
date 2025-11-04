package com.example.foodstore.service;

import com.example.foodstore.dto.request.CategoriaRegister;
import com.example.foodstore.dto.request.CategoriaEdit;
import com.example.foodstore.dto.response.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {

    /**
     * Crea una nueva categoría
     */
    CategoriaResponseDTO crear(CategoriaRegister categoriaRegister);

    /**
     * Busca una categoría por ID
     */
    CategoriaResponseDTO buscarPorId(Long id);

    /**
     * Busca todas las categorías (para admin)
     */
    List<CategoriaResponseDTO> buscarTodas();

    /**
     * Busca categorías con productos disponibles (para tienda)
     */
    List<CategoriaResponseDTO> buscarConProductosDisponibles();

    /**
     * Actualiza una categoría existente
     */
    CategoriaResponseDTO actualizar(Long id, CategoriaEdit categoriaEdit);

    /**
     * Elimina una categoría por ID
     */
    void eliminar(Long id);

    void restaurar(Long id);
}