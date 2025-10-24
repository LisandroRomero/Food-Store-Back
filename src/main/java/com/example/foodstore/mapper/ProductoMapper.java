package com.example.foodstore.mapper;

import com.example.foodstore.dto.request.ProductoRegister;
import com.example.foodstore.dto.request.ProductoEdit;
import com.example.foodstore.dto.response.ProductoResponseDTO;
import com.example.foodstore.entity.Producto;

public class ProductoMapper {

    public static ProductoResponseDTO toDTO(Producto producto) {
        return ProductoResponseDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .build();
    }

    public static Producto toEntity(ProductoRegister productoCreate) {
        return Producto.builder()
                .nombre(productoCreate.getNombre())
                .precio(productoCreate.getPrecio())
                .build();
    }

    public static void updateEntityFromEdit(Producto producto, ProductoEdit productoEdit) {
        if (productoEdit.getNombre() != null) {
            producto.setNombre(productoEdit.getNombre());
        }
        producto.setPrecio(productoEdit.getPrecio());
    }
}
