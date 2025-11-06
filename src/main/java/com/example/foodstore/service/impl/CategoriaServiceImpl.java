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

        // Buscar categoría padre si se especificó
        Categoria categoriaPadre = null;
        if (categoriaRegister.getCategoriaPadreId() != null) {
            categoriaPadre = categoriaRepository.findById(categoriaRegister.getCategoriaPadreId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Categoría padre no encontrada con ID: " + categoriaRegister.getCategoriaPadreId()));
        }

        // Convertir DTO a entidad y guardar
        Categoria categoria = categoriaMapper.toEntity(categoriaRegister);
        categoria.setCategoriaPadre(categoriaPadre);
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
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> buscarCategoriasRaiz() {
        log.debug("Buscando categorías raíz");

        List<Categoria> categoriasRaiz = categoriaRepository.findByCategoriaPadreIsNullAndActivoTrue();
        return categoriasRaiz.stream()
                .map(categoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> buscarArbolCategorias() {
        log.debug("Buscando árbol completo de categorías");

        List<Categoria> categoriasRaiz = categoriaRepository.findByCategoriaPadreIsNullAndActivoTrue();
        return categoriasRaiz.stream()
                .map(categoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> buscarSubcategorias(Long categoriaPadreId) {
        log.debug("Buscando subcategorías para padre ID: {}", categoriaPadreId);

        // Verificar que la categoría padre existe
        if (!categoriaRepository.existsById(categoriaPadreId)) {
            throw new ResourceNotFoundException("Categoría padre no encontrada con ID: " + categoriaPadreId);
        }

        List<Categoria> subcategorias = categoriaRepository.findByCategoriaPadreIdAndActivoTrue(categoriaPadreId);
        return subcategorias.stream()
                .map(categoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaResponseDTO moverCategoria(Long categoriaId, Long nuevoPadreId) {
        log.debug("Moviendo categoría ID: {} a nuevo padre ID: {}", categoriaId, nuevoPadreId);

        // Buscar categoría a mover
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + categoriaId));

        // Buscar nuevo padre (si se especificó)
        Categoria nuevoPadre = null;
        if (nuevoPadreId != null) {
            nuevoPadre = categoriaRepository.findById(nuevoPadreId)
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría padre no encontrada con ID: " + nuevoPadreId));

            // Validar que no se cree ciclo (una categoría no puede ser padre de sí misma)
            if (categoriaId.equals(nuevoPadreId)) {
                throw new IllegalArgumentException("Una categoría no puede ser padre de sí misma");
            }

            // Validar ciclos en el árbol
            if (tieneCiclo(categoria, nuevoPadre)) {
                throw new IllegalArgumentException("No se puede mover la categoría porque crearía un ciclo en el árbol");
            }
        }

        categoria.setCategoriaPadre(nuevoPadre);
        Categoria categoriaActualizada = categoriaRepository.save(categoria);

        log.info("Categoría movida exitosamente");
        return categoriaMapper.toResponseDTO(categoriaActualizada);
    }

    /**
     * Metodo auxiliar para detectar ciclos en el árbol
     */
    private boolean tieneCiclo(Categoria categoria, Categoria posiblePadre) {
        Categoria actual = posiblePadre;
        while (actual != null) {
            if (actual.getId().equals(categoria.getId())) {
                return true; // Se encontró ciclo
            }
            actual = actual.getCategoriaPadre();
        }
        return false;
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