package com.example.foodstore.service.impl;

import com.example.foodstore.dto.request.CarritoCompraRequest;
import com.example.foodstore.dto.request.ItemCarritoRequest;
import com.example.foodstore.dto.request.PedidoRegister;
import com.example.foodstore.dto.request.PedidoEdit;
import com.example.foodstore.dto.response.PedidoResponseDTO;
import com.example.foodstore.entity.*;
import com.example.foodstore.mapper.PedidoMapper;
import com.example.foodstore.repository.PedidoRepository;
import com.example.foodstore.repository.ProductoRepository;
import com.example.foodstore.repository.UsuarioRepository;
import com.example.foodstore.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public PedidoResponseDTO crear(PedidoRegister pedidoCreate) {
        Pedido pedido = PedidoMapper.toEntity(pedidoCreate);

        // Buscar y asignar el usuario
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(pedidoCreate.getUsuarioId());
        if (usuarioOptional.isPresent()) {
            pedido.setUsuario(usuarioOptional.get());
            pedido = pedidoRepository.save(pedido);
            return PedidoMapper.toDTO(pedido);
        }
        return null;
    }

    @Override
    public PedidoResponseDTO crearPedidoDesdeCarrito(CarritoCompraRequest carritoRequest) {
        try {
            // 1. Validar que el usuario existe
            Usuario usuario = usuarioRepository.findById(carritoRequest.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + carritoRequest.getUsuarioId()));

            // 2. Validar que hay items en el carrito
            if (carritoRequest.getItems() == null || carritoRequest.getItems().isEmpty()) {
                throw new RuntimeException("El carrito está vacío");
            }

            // 3. Crear pedido base
            Pedido pedido = Pedido.builder()
                    .fecha(LocalDate.now())
                    .estado(Estado.PENDIENTE)
                    .total(0.0)
                    .usuario(usuario)
                    .build();

            double totalPedido = 0.0;

            // 4. Procesar cada item del carrito
            for (ItemCarritoRequest item : carritoRequest.getItems()) {
                Producto producto = productoRepository.findById(item.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + item.getProductoId()));

                // Validar que el producto está disponible y activo
                if (!producto.isDisponible() || !producto.isActivo()) {
                    throw new RuntimeException("El producto no está disponible: " + producto.getNombre());
                }

                // Validar stock suficiente
                if (producto.getStock() < item.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para: " + producto.getNombre() +
                            ". Stock disponible: " + producto.getStock() +
                            ", solicitado: " + item.getCantidad());
                }

                // Calcular subtotal (precio * cantidad)
                double subtotal = producto.getPrecio() * item.getCantidad();
                totalPedido += subtotal;

                // Crear detalle del pedido
                DetallePedido detalle = DetallePedido.builder()
                        .cantidad(item.getCantidad())
                        .subtotal(subtotal)
                        .producto(producto)
                        .pedido(pedido)  // Relación bidireccional
                        .build();

                // Agregar detalle al pedido usando el metodo helper
                pedido.addDetalle(detalle);

                // Actualizar stock del producto
                producto.setStock(producto.getStock() - item.getCantidad());
                productoRepository.save(producto);
            }

            // 5. Asignar total calculado y guardar pedido
            pedido.setTotal(totalPedido);
            Pedido pedidoGuardado = pedidoRepository.save(pedido);

            // 6. Retornar DTO con todos los detalles
            return PedidoMapper.toDTO(pedidoGuardado);

        } catch (RuntimeException e) {
            // Relanzar excepciones de negocio
            throw e;
        } catch (Exception e) {
            // Capturar cualquier otra excepción
            throw new RuntimeException("Error al procesar el pedido: " + e.getMessage());
        }
    }

    @Override
    public PedidoResponseDTO actualizar(Long id, PedidoEdit pedidoEdit) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            PedidoMapper.updateEntityFromEdit(pedido, pedidoEdit);
            pedido = pedidoRepository.save(pedido);
            return PedidoMapper.toDTO(pedido);
        }
        return null;
    }

    @Override
    public PedidoResponseDTO buscarId(Long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            return PedidoMapper.toDTO(pedidoOptional.get());
        }
        return null;
    }

    @Override
    public List<PedidoResponseDTO> buscaTodos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(PedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
        }
    }

    @Override
    public List<PedidoResponseDTO> buscarPorUsuario(Long usuarioId) {
        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(usuarioId);
        return pedidos.stream()
                .map(PedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoResponseDTO> buscarPorEstado(String estado) {
        List<Pedido> pedidos = pedidoRepository.findByEstado(Estado.valueOf(estado));
        return pedidos.stream()
                .map(PedidoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
