package com.example.foodstore.service.impl;

import com.example.foodstore.dto.DetallePedidoCreate;
import com.example.foodstore.dto.DetallePedidoDto;
import com.example.foodstore.dto.DetallePedidoEdit;
import com.example.foodstore.entity.DetallePedido;
import com.example.foodstore.entity.Pedido;
import com.example.foodstore.entity.Producto;
import com.example.foodstore.mapper.DetallePedidoMapper;
import com.example.foodstore.repository.DetallePedidoRepository;
import com.example.foodstore.repository.PedidoRepository;
import com.example.foodstore.repository.ProductoRepository;
import com.example.foodstore.service.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public DetallePedidoDto crear(DetallePedidoCreate detallePedidoCreate) {
        DetallePedido detallePedido = DetallePedidoMapper.toEntity(detallePedidoCreate);

        // Buscar y asignar el pedido
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(detallePedidoCreate.getPedidoId());
        Optional<Producto> productoOptional = productoRepository.findById(detallePedidoCreate.getProductoId());

        if (pedidoOptional.isPresent() && !pedidoOptional.get().isEliminado() &&
                productoOptional.isPresent() && !productoOptional.get().isEliminado()) {

            detallePedido.setPedido(pedidoOptional.get());
            detallePedido.setProducto(productoOptional.get());
            detallePedido = detallePedidoRepository.save(detallePedido);
            return DetallePedidoMapper.toDTO(detallePedido);
        }
        return null;
    }

    @Override
    public DetallePedidoDto actualizar(Long id, DetallePedidoEdit detallePedidoEdit) {
        Optional<DetallePedido> detallePedidoOptional = detallePedidoRepository.findById(id);
        if (detallePedidoOptional.isPresent()) {
            DetallePedido detallePedido = detallePedidoOptional.get();
            DetallePedidoMapper.updateEntityFromEdit(detallePedido, detallePedidoEdit);
            detallePedido = detallePedidoRepository.save(detallePedido);
            return DetallePedidoMapper.toDTO(detallePedido);
        }
        return null;
    }

    @Override
    public DetallePedidoDto buscarId(Long id) {
        Optional<DetallePedido> detallePedidoOptional = detallePedidoRepository.findById(id);
        if (detallePedidoOptional.isPresent()) {
            if (!detallePedidoOptional.get().isEliminado()) {
                return DetallePedidoMapper.toDTO(detallePedidoOptional.get());
            }
        }
        return null;
    }

    @Override
    public List<DetallePedidoDto> buscaTodos() {
        List<DetallePedido> detalles = detallePedidoRepository.findAllByEliminadoFalse();
        return detalles.stream()
                .map(DetallePedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        Optional<DetallePedido> detallePedidoOptional = detallePedidoRepository.findById(id);
        if (detallePedidoOptional.isPresent()) {
            DetallePedido detallePedido = detallePedidoOptional.get();
            detallePedido.setEliminado(true);
            detallePedidoRepository.save(detallePedido);
        }
    }

    @Override
    public List<DetallePedidoDto> buscarPorPedido(Long pedidoId) {
        List<DetallePedido> detalles = detallePedidoRepository.findByPedidoIdAndEliminadoFalse(pedidoId);
        return detalles.stream()
                .map(DetallePedidoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
