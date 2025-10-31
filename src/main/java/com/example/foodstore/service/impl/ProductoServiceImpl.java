package com.example.foodstore.service.impl;

import com.example.foodstore.dto.request.ProductoRegister;
import com.example.foodstore.dto.request.ProductoEdit;
import com.example.foodstore.dto.response.ProductoResponseDTO;
import com.example.foodstore.entity.Categoria;
import com.example.foodstore.entity.Producto;
import com.example.foodstore.mapper.ProductoMapper;
import com.example.foodstore.repository.CategoriaRepository;
import com.example.foodstore.repository.ProductoRepository;
import com.example.foodstore.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public ProductoResponseDTO crear(ProductoRegister dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + dto.getCategoriaId()));

        Producto producto = ProductoMapper.toEntity(dto, categoria);
        producto = productoRepository.save(producto);
        return ProductoMapper.toDTO(producto);
    }

    @Override
    public ProductoResponseDTO actualizar(Long id, ProductoEdit dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        Categoria categoria = null;
        if (dto.getCategoriaId() != null) {
            categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + dto.getCategoriaId()));
        }

        ProductoMapper.updateEntityFromEdit(producto, dto, categoria);
        producto = productoRepository.save(producto);
        return ProductoMapper.toDTO(producto);
    }

    @Override
    public ProductoResponseDTO buscarId(Long id) {
        return productoRepository.findById(id)
                .filter(Producto::isActivo)
                .map(ProductoMapper::toDTO)
                .orElse(null);
    }


    @Override
    public List<ProductoResponseDTO> buscaTodos() {
        return productoRepository.findByActivoTrue()
                .stream()
                .map(ProductoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setActivo(false); // 🔹 Desactivamos el producto
        productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void restaurar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setActivo(true); // 🔹 Volvemos a activar el producto
        productoRepository.save(producto);
    }

    @Override
    public List<ProductoResponseDTO> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(ProductoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResponseDTO> filtrarProductos(Long categoriaId, Boolean disponible, Boolean conStock) {
        return productoRepository.findByActivoTrue()
                .stream()
                .filter(p -> categoriaId == null || p.getCategoria().getId().equals(categoriaId))
                .filter(p -> disponible == null || p.isDisponible() == disponible)
                .filter(p -> conStock == null || (conStock ? p.getStock() > 0 : true))
                .map(ProductoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResponseDTO> buscaTodosAdmin() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoMapper::toDTO)
                .collect(Collectors.toList());
    }

}

