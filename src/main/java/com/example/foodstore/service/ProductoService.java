package com.example.foodstore.service;

import com.example.foodstore.dto.request.ProductoRegister;
import com.example.foodstore.dto.request.ProductoEdit;
import com.example.foodstore.dto.response.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {
    ProductoResponseDTO crear(ProductoRegister productoCreate);
    ProductoResponseDTO actualizar(Long id, ProductoEdit productoEdit);
    ProductoResponseDTO buscarId(Long id);
    List<ProductoResponseDTO> buscaTodos();
    void eliminar(Long id);
    List<ProductoResponseDTO> buscarPorNombre(String nombre);
}
