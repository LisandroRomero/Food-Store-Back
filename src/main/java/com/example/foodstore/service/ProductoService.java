package com.example.foodstore.service;

import com.example.foodstore.dto.ProductoCreate;
import com.example.foodstore.dto.ProductoDto;
import com.example.foodstore.dto.ProductoEdit;
import java.util.List;

public interface ProductoService {
    ProductoDto crear(ProductoCreate productoCreate);
    ProductoDto actualizar(Long id, ProductoEdit productoEdit);
    ProductoDto buscarId(Long id);
    List<ProductoDto> buscaTodos();
    void eliminar(Long id);
    List<ProductoDto> buscarPorNombre(String nombre);
}
