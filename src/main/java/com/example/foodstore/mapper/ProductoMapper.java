package com.example.foodstore.mapper;

import com.example.foodstore.dto.request.ProductoRegister;
import com.example.foodstore.dto.request.ProductoEdit;
import com.example.foodstore.dto.response.CategoriaResponseDTO;
import com.example.foodstore.dto.response.ProductoResponseDTO;
import com.example.foodstore.entity.Categoria;
import com.example.foodstore.entity.Producto;

public class ProductoMapper {

    public static ProductoResponseDTO toDTO(Producto producto) {
        return ProductoResponseDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .disponible(producto.isDisponible())
                .activo(producto.isActivo())
                .categoria(
                        CategoriaResponseDTO.builder()
                                .id(producto.getCategoria().getId())
                                .nombre(producto.getCategoria().getNombre())
                                .descripcion(producto.getCategoria().getDescripcion())
                                .imagen(producto.getCategoria().getImagen())
                                .activo(producto.getCategoria().isActivo())
                                .build()
                )
                .build();
    }

    public static Producto toEntity(ProductoRegister dto, Categoria categoria) {
        return Producto.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .precio(dto.getPrecio())
                .stock(dto.getStock())
                .disponible(dto.isDisponible())
                .imagen(dto.getImagen())
                .categoria(categoria)
                .build();
    }

    public static void updateEntityFromEdit(Producto producto, ProductoEdit dto, Categoria categoria) {
        if (dto.getNombre() != null) producto.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) producto.setDescripcion(dto.getDescripcion());
        if (dto.getPrecio() != null) producto.setPrecio(dto.getPrecio());
        if (dto.getStock() != null) producto.setStock(dto.getStock());
        if (dto.getDisponible() != null) producto.setDisponible(dto.getDisponible());
        if (dto.getImagen() != null) producto.setImagen(dto.getImagen());
        if (categoria != null) producto.setCategoria(categoria);
    }
}

