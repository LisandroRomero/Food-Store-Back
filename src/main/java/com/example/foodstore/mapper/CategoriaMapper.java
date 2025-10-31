package com.example.foodstore.mapper;

import com.example.foodstore.dto.request.CategoriaEdit;
import com.example.foodstore.dto.request.CategoriaRegister;
import com.example.foodstore.dto.response.CategoriaResponseDTO;
import com.example.foodstore.entity.Categoria;

public class CategoriaMapper {

    public static CategoriaResponseDTO toDTO(Categoria categoria) {
        return CategoriaResponseDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .imagen(categoria.getImagen())
                .activo(categoria.isActivo())
                .build();
    }

    public static Categoria toEntity(CategoriaRegister categoriaCreate) {
        return Categoria.builder()
                .nombre(categoriaCreate.getNombre())
                .descripcion(categoriaCreate.getDescripcion())
                .imagen(categoriaCreate.getImagen())
                .build();
    }

    public static void updateEntityFromEdit(Categoria categoria, CategoriaEdit categoriaEdit) {
        if (categoriaEdit.getNombre() != null)
            categoria.setNombre(categoriaEdit.getNombre());
        if (categoriaEdit.getDescripcion() != null)
            categoria.setDescripcion(categoriaEdit.getDescripcion());
        if (categoriaEdit.getImagen() != null)
            categoria.setImagen(categoriaEdit.getImagen());
    }
}

