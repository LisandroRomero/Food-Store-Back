package com.example.foodstore.service.impl;

import com.example.foodstore.dto.request.ProductoRegister;
import com.example.foodstore.dto.request.ProductoEdit;
import com.example.foodstore.dto.response.ProductoResponseDTO;
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
    public ProductoResponseDTO crear(ProductoRegister productoCreate) {
        Producto producto = ProductoMapper.toEntity(productoCreate);
        producto = productoRepository.save(producto);
        return ProductoMapper.toDTO(producto);
    }

    @Override
    public ProductoResponseDTO actualizar(Long id, ProductoEdit productoEdit) {
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
    public ProductoResponseDTO buscarId(Long id) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isPresent()) {
            return ProductoMapper.toDTO(productoOptional.get());
        }
        return null;
    }

    @Override
    public List<ProductoResponseDTO> buscaTodos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(ProductoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
        }
    }

    @Override
    public List<ProductoResponseDTO> buscarPorNombre(String nombre) {
        List<Producto> productos = productoRepository.findByNombreContainingIgnoreCase(nombre);
        return productos.stream()
                .map(ProductoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
