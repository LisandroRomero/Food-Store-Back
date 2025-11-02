package com.example.foodstore.mapper;

import com.example.foodstore.dto.request.CategoriaRegister;
import com.example.foodstore.dto.request.CategoriaEdit;
import com.example.foodstore.dto.response.CategoriaResponseDTO;
import com.example.foodstore.dto.response.CategoriaSimpleDTO;
import com.example.foodstore.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    /**
     * Convierte un CategoriaRegister DTO a entidad Categoria
     */
    public Categoria toEntity(CategoriaRegister categoriaRegister) {
        if (categoriaRegister == null) {
            return null;
        }

        return Categoria.builder()
                .nombre(categoriaRegister.getNombre())
                .descripcion(categoriaRegister.getDescripcion())
                .imagen(categoriaRegister.getImagen())
                .build();
    }

    /**
     * Convierte una entidad Categoria a CategoriaResponseDTO
     */
    public CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        if (categoria == null) {
            return null;
        }

        return CategoriaResponseDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .imagen(categoria.getImagen())
                .totalProductos(categoria.getProductos() != null ? categoria.getProductos().size() : 0)
                .build();
    }

    /**
     * Convierte una entidad Categoria a CategoriaSimpleDTO (para usar en otros DTOs)
     */
    public CategoriaSimpleDTO toSimpleDTO(Categoria categoria) {
        if (categoria == null) {
            return null;
        }

        return CategoriaSimpleDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .build();
    }

    /**
     * Actualiza una entidad Categoria existente con datos de CategoriaEdit
     */
    public void updateEntityFromEdit(Categoria categoria, CategoriaEdit categoriaEdit) {
        if (categoria == null || categoriaEdit == null) {
            return;
        }

        if (categoriaEdit.getNombre() != null && !categoriaEdit.getNombre().trim().isEmpty()) {
            categoria.setNombre(categoriaEdit.getNombre().trim());
        }

        if (categoriaEdit.getDescripcion() != null && !categoriaEdit.getDescripcion().trim().isEmpty()) {
            categoria.setDescripcion(categoriaEdit.getDescripcion().trim());
        }

        if (categoriaEdit.getImagen() != null) {
            categoria.setImagen(categoriaEdit.getImagen().trim());
        }
    }
}