package com.example.foodstore.service.impl;

import com.example.foodstore.dto.request.ProductoRegister;
import com.example.foodstore.dto.request.ProductoEdit;
import com.example.foodstore.dto.response.ProductoResponseDTO;
import com.example.foodstore.entity.Producto;
import com.example.foodstore.entity.Categoria;
import com.example.foodstore.excepcion.DuplicateResourceException;
import com.example.foodstore.excepcion.ResourceNotFoundException;
import com.example.foodstore.mapper.ProductoMapper;
import com.example.foodstore.repository.ProductoRepository;
import com.example.foodstore.repository.CategoriaRepository;
import com.example.foodstore.service.ProductoService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Override
    public ProductoResponseDTO crear(ProductoRegister productoRegister) {
        log.debug("Creando nuevo producto: {}", productoRegister.getNombre());

        // Validar que no exista un producto con el mismo nombre
        if (productoRepository.existsByNombreIgnoreCase(productoRegister.getNombre())) {
            throw new DuplicateResourceException("Ya existe un producto con el nombre: " + productoRegister.getNombre());
        }

        // Buscar la categoría
        Categoria categoria = categoriaRepository.findById(productoRegister.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + productoRegister.getCategoriaId()));

        // Convertir DTO a entidad y guardar
        Producto producto = productoMapper.toEntity(productoRegister, categoria);
        Producto productoGuardado = productoRepository.save(producto);

        log.info("Producto creado exitosamente con ID: {}", productoGuardado.getId());
        return productoMapper.toResponseDTO(productoGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO buscarPorId(Long id) {
        log.debug("Buscando producto por ID: {}", id);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        return productoMapper.toResponseDTO(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> buscarTodos() {
        log.debug("Buscando todos los productos");

        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(productoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoResponseDTO> buscarConPaginacion(Pageable pageable) {
        log.debug("Buscando productos con paginación: página {}, tamaño {}", pageable.getPageNumber(), pageable.getPageSize());

        Page<Producto> productos = productoRepository.findAll(pageable);
        return productos.map(productoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> buscarPorCategoria(Long categoriaId) {
        log.debug("Buscando productos por categoría ID: {}", categoriaId);

        // Verificar que la categoría existe
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new ResourceNotFoundException("Categoría no encontrada con ID: " + categoriaId);
        }

        List<Producto> productos = productoRepository.findByCategoriaId(categoriaId);
        return productos.stream()
                .map(productoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> buscarDisponibles() {
        log.debug("Buscando productos disponibles para la tienda");

        List<Producto> productos = productoRepository.findByDisponibleTrue();
        return productos.stream()
                .map(productoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    public ProductoResponseDTO actualizar(Long id, ProductoEdit productoEdit) {
        log.debug("Actualizando producto con ID: {}", id);

        // Buscar el producto existente
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        // Validar nombre único si se está cambiando
        if (productoEdit.getNombre() != null && !productoEdit.getNombre().trim().isEmpty()) {
            String nuevoNombre = productoEdit.getNombre().trim();
            if (!producto.getNombre().equalsIgnoreCase(nuevoNombre) &&
                productoRepository.existsByNombreIgnoreCaseAndIdNot(nuevoNombre, id)) {
                throw new DuplicateResourceException("Ya existe un producto con el nombre: " + nuevoNombre);
            }
        }

        // Buscar nueva categoría si se especifica
        Categoria nuevaCategoria = null;
        if (productoEdit.getCategoriaId() != null) {
            nuevaCategoria = categoriaRepository.findById(productoEdit.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + productoEdit.getCategoriaId()));
        }

        // Actualizar la entidad
        productoMapper.updateEntityFromEdit(producto, productoEdit, nuevaCategoria);
        Producto productoActualizado = productoRepository.save(producto);

        log.info("Producto actualizado exitosamente con ID: {}", id);
        return productoMapper.toResponseDTO(productoActualizado);
    }

    @Override
    public void eliminar(Long id) {
        log.debug("Eliminando producto con ID: {}", id);

        // Verificar que el producto existe
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }

        productoRepository.deleteById(id);
        log.info("Producto eliminado exitosamente con ID: {}", id);
    }


    @Override
    public ProductoResponseDTO cambiarDisponibilidad(Long id, boolean disponible) {
        log.debug("Cambiando disponibilidad del producto ID: {} a {}", id, disponible);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        producto.setDisponible(disponible);
        Producto productoActualizado = productoRepository.save(producto);

        log.info("Disponibilidad cambiada exitosamente para producto ID: {}", id);
        return productoMapper.toResponseDTO(productoActualizado);
    }
}