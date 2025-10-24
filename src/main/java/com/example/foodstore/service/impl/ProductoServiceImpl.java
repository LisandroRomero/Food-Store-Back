package com.example.foodstore.service.impl;

import com.example.foodstore.dto.ProductoCreate;
import com.example.foodstore.dto.ProductoDto;
import com.example.foodstore.dto.ProductoEdit;
import com.example.foodstore.entity.Producto;
import com.example.foodstore.mapper.ProductoMapper;
import com.example.foodstore.repository.ProductoRepository;
import com.example.foodstore.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public ProductoDto crear(ProductoCreate productoCreate) {
        Producto producto = ProductoMapper.toEntity(productoCreate);
        producto = productoRepository.save(producto);
        return ProductoMapper.toDTO(producto);
    }

    @Override
    public ProductoDto actualizar(Long id, ProductoEdit productoEdit) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            ProductoMapper.updateEntityFromEdit(producto, productoEdit);
            producto = productoRepository.save(producto);
            return ProductoMapper.toDTO(producto);
        }
        return null;
    }

    @Override
    public ProductoDto buscarId(Long id) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isPresent()) {
            if (!productoOptional.get().isEliminado()) {
                return ProductoMapper.toDTO(productoOptional.get());
            }
        }
        return null;
    }

    @Override
    public List<ProductoDto> buscaTodos() {
        List<Producto> productos = productoRepository.findAllByEliminadoFalse();
        return productos.stream()
                .map(ProductoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            producto.setEliminado(true);
            productoRepository.save(producto);
        }
    }

    @Override
    public List<ProductoDto> buscarPorNombre(String nombre) {
        List<Producto> productos = productoRepository.findByNombreContainingIgnoreCaseAndEliminadoFalse(nombre);
        return productos.stream()
                .map(ProductoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
