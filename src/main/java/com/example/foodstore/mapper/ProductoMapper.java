package com.example.foodstore.mapper;

import com.example.foodstore.dto.ProductoCreate;
import com.example.foodstore.dto.ProductoDto;
import com.example.foodstore.dto.ProductoEdit;
import com.example.foodstore.entity.Producto;

public class ProductoMapper {

    public static ProductoDto toDTO(Producto producto) {
        return ProductoDto.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .build();
    }

    public static Producto toEntity(ProductoCreate productoCreate) {
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
