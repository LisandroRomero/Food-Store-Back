package com.example.foodstore.mapper;

import com.example.foodstore.dto.request.ProductoRegister;
import com.example.foodstore.dto.request.ProductoEdit;
import com.example.foodstore.dto.response.ProductoResponseDTO;
import com.example.foodstore.entity.Producto;
import com.example.foodstore.entity.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    @Autowired
    private CategoriaMapper categoriaMapper;

    /**
     * Convierte un ProductoRegister DTO a entidad Producto
     */
    public Producto toEntity(ProductoRegister productoRegister, Categoria categoria) {
        if (productoRegister == null) {
            return null;
        }

        return Producto.builder()
                .nombre(productoRegister.getNombre())
                .descripcion(productoRegister.getDescripcion())
                .precio(productoRegister.getPrecio())
                .stock(productoRegister.getStock())
                .disponible(productoRegister.isDisponible())
                .imagen(productoRegister.getImagen())
                .categoria(categoria)
                .build();
    }

    /**
     * Convierte una entidad Producto a ProductoResponseDTO
     */
    public ProductoResponseDTO toResponseDTO(Producto producto) {
        if (producto == null) {
            return null;
        }

        return ProductoResponseDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .disponible(producto.isDisponible())
                .imagen(producto.getImagen())
                .categoria(categoriaMapper.toSimpleDTO(producto.getCategoria()))
                .build();
    }

    /**
     * Actualiza una entidad Producto existente con datos de ProductoEdit
     */
    public void updateEntityFromEdit(Producto producto, ProductoEdit productoEdit, Categoria categoria) {
        if (producto == null || productoEdit == null) {
            return;
        }

        if (productoEdit.getNombre() != null && !productoEdit.getNombre().trim().isEmpty()) {
            producto.setNombre(productoEdit.getNombre().trim());
        }

        if (productoEdit.getDescripcion() != null && !productoEdit.getDescripcion().trim().isEmpty()) {
            producto.setDescripcion(productoEdit.getDescripcion().trim());
        }

        if (productoEdit.getPrecio() != null) {
            producto.setPrecio(productoEdit.getPrecio());
        }

        if (productoEdit.getStock() != null) {
            producto.setStock(productoEdit.getStock());
        }

        if (productoEdit.getDisponible() != null) {
            producto.setDisponible(productoEdit.getDisponible());
        }

        if (productoEdit.getImagen() != null) {
            producto.setImagen(productoEdit.getImagen().trim());
        }

        if (categoria != null) {
            producto.setCategoria(categoria);
        }
    }
}