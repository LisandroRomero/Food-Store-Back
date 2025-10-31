package com.example.foodstore.service;

import com.example.foodstore.dto.request.CategoriaEdit;
import com.example.foodstore.dto.request.CategoriaRegister;
import com.example.foodstore.dto.response.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {
    CategoriaResponseDTO crear(CategoriaRegister categoriaCreate);
    CategoriaResponseDTO actualizar(Long id, CategoriaEdit categoriaEdit);
    CategoriaResponseDTO buscarId(Long id);
    List<CategoriaResponseDTO> buscaTodos();
    void eliminar(Long id);
    void restaurar(Long id);
    List<CategoriaResponseDTO> buscaTodosAdmin();
}