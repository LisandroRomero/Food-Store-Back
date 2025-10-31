package com.example.foodstore.service.impl;

import com.example.foodstore.dto.request.CategoriaEdit;
import com.example.foodstore.dto.request.CategoriaRegister;
import com.example.foodstore.dto.response.CategoriaResponseDTO;
import com.example.foodstore.entity.Categoria;
import com.example.foodstore.mapper.CategoriaMapper;
import com.example.foodstore.repository.CategoriaRepository;
import com.example.foodstore.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public CategoriaResponseDTO crear(CategoriaRegister categoriaCreate) {
        Categoria categoria = CategoriaMapper.toEntity(categoriaCreate);
        categoria = categoriaRepository.save(categoria);
        return CategoriaMapper.toDTO(categoria);
    }

    @Override
    public CategoriaResponseDTO actualizar(Long id, CategoriaEdit categoriaEdit) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        if (categoriaOpt.isPresent()) {
            Categoria categoria = categoriaOpt.get();
            CategoriaMapper.updateEntityFromEdit(categoria, categoriaEdit);
            categoria = categoriaRepository.save(categoria);
            return CategoriaMapper.toDTO(categoria);
        }
        return null;
    }

    @Override
    public CategoriaResponseDTO buscarId(Long id) {
        return categoriaRepository.findById(id)
                .filter(Categoria::isActivo)
                .map(CategoriaMapper::toDTO)
                .orElse(null);
    }

    @Override
    public List<CategoriaResponseDTO> buscaTodos() {
        return categoriaRepository.findByActivoTrue()
                .stream()
                .map(CategoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Marcamos la categoría como inactiva
        categoria.setActivo(false);

        // Marcamos todos los productos asociados como inactivos
        if (categoria.getProductos() != null) {
            categoria.getProductos().forEach(producto -> producto.setActivo(false));
        }

        categoriaRepository.save(categoria);
    }

    @Override
    @Transactional
    public void restaurar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoria.setActivo(true);

        if (categoria.getProductos() != null) {
            categoria.getProductos().forEach(p -> p.setActivo(true));
        }

        categoriaRepository.save(categoria);
    }

    @Override
    public List<CategoriaResponseDTO> buscaTodosAdmin() {
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

}