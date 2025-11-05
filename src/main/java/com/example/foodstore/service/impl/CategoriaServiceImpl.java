package com.example.foodstore.service.impl;

import com.example.foodstore.dto.request.CategoriaRegister;
import com.example.foodstore.dto.request.CategoriaEdit;
import com.example.foodstore.dto.response.CategoriaResponseDTO;
import com.example.foodstore.entity.Categoria;
import com.example.foodstore.excepcion.DuplicateResourceException;
import com.example.foodstore.excepcion.ResourceNotFoundException;
import com.example.foodstore.mapper.CategoriaMapper;
import com.example.foodstore.repository.CategoriaRepository;
import com.example.foodstore.service.CategoriaService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Override
    public CategoriaResponseDTO crear(CategoriaRegister categoriaRegister) {
        log.debug("Creando nueva categoría: {}", categoriaRegister.getNombre());

        // Validar que no exista una categoría con el mismo nombre
        if (categoriaRepository.existsByNombreIgnoreCase(categoriaRegister.getNombre())) {
            throw new DuplicateResourceException("Ya existe una categoría con el nombre: " + categoriaRegister.getNombre());
        }

        // Convertir DTO a entidad y guardar
        Categoria categoria = categoriaMapper.toEntity(categoriaRegister);
        Categoria categoriaGuardada = categoriaRepository.save(categoria);

        log.info("Categoría creada exitosamente con ID: {}", categoriaGuardada.getId());
        return categoriaMapper.toResponseDTO(categoriaGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(Long id) {
        log.debug("Buscando categoría por ID: {}", id);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));

        return categoriaMapper.toResponseDTO(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> buscarTodas() {
        log.debug("Buscando todas las categorías");

        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(categoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> buscarConProductosDisponibles() {
        log.debug("Buscando categorías con productos disponibles");

        List<Categoria> categorias = categoriaRepository.findCategoriasConProductosDisponibles();
        return categorias.stream()
                .map(categoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    public CategoriaResponseDTO actualizar(Long id, CategoriaEdit categoriaEdit) {
        log.debug("Actualizando categoría con ID: {}", id);

        // Buscar la categoría existente
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));

        // Validar nombre único si se está cambiando
        if (categoriaEdit.getNombre() != null && !categoriaEdit.getNombre().trim().isEmpty()) {
            String nuevoNombre = categoriaEdit.getNombre().trim();
            if (!categoria.getNombre().equalsIgnoreCase(nuevoNombre) &&
                categoriaRepository.existsByNombreIgnoreCaseAndIdNot(nuevoNombre, id)) {
                throw new DuplicateResourceException("Ya existe una categoría con el nombre: " + nuevoNombre);
            }
        }

        // Actualizar la entidad
        categoriaMapper.updateEntityFromEdit(categoria, categoriaEdit);
        Categoria categoriaActualizada = categoriaRepository.save(categoria);

        log.info("Categoría actualizada exitosamente con ID: {}", id);
        return categoriaMapper.toResponseDTO(categoriaActualizada);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando categoria con ID: {}", id);
        Categoria categoria = categoriaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

            // Marcamos la categoría como inactiva
            categoria.setActivo(false);

            // Marcamos todos los productos asociados como inactivos
            if (categoria.getProductos() != null) {
                categoria.getProductos().forEach(producto -> producto.setActivo(false));
            }
            log.info("Categoría con ID: {} marcada como inactiva", id);
            categoriaRepository.save(categoria);
    }

    @Override
    public void restaurar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoria.setActivo(true);

        if (categoria.getProductos() != null) {
            categoria.getProductos().forEach(p -> p.setActivo(true));
        }

        categoriaRepository.save(categoria);
    }



}