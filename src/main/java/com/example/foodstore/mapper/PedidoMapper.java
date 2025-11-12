package com.example.foodstore.mapper;

import com.example.foodstore.dto.request.PedidoRegister;
import com.example.foodstore.dto.request.PedidoEdit;
import com.example.foodstore.dto.response.PedidoResponseDTO;
import com.example.foodstore.dto.response.DetallePedidoResponseDTO;
import com.example.foodstore.dto.response.ProductoResponseDTO;
import com.example.foodstore.dto.response.CategoriaSimpleDTO;
import com.example.foodstore.entity.Pedido;
import com.example.foodstore.entity.DetallePedido;
import com.example.foodstore.entity.Producto;
import com.example.foodstore.entity.Categoria;
import com.example.foodstore.entity.Estado;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {

    public static PedidoResponseDTO toDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        PedidoResponseDTO response = PedidoResponseDTO.builder()
                .id(pedido.getId())
                .fecha(pedido.getFecha())
                .estado(pedido.getEstado().name())
                .total(pedido.getTotal())
                .usuario(UsuarioMapper.toDTO(pedido.getUsuario()))
                .build();

        // Mapear detalles si existen
        if (pedido.getDetalles() != null && !pedido.getDetalles().isEmpty()) {
            List<DetallePedidoResponseDTO> detallesDTO = pedido.getDetalles().stream()
                    .map(PedidoMapper::mapDetalleToDTO)
                    .collect(Collectors.toList());
            response.setDetalles(detallesDTO);
        }

        return response;
    }

    // Metodo auxiliar para mapear detalle
    private static DetallePedidoResponseDTO mapDetalleToDTO(DetallePedido detalle) {
        if (detalle == null) {
            return null;
        }

        return DetallePedidoResponseDTO.builder()
                .id(detalle.getId())
                .cantidad(detalle.getCantidad())
                .subtotal(detalle.getSubtotal())
                .producto(mapProductoToDTO(detalle.getProducto()))
                .build();
    }

    // Metodo auxiliar para mapear producto (simplificado)
    private static ProductoResponseDTO mapProductoToDTO(Producto producto) {
        if (producto == null) {
            return null;
        }

        return ProductoResponseDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .descripcion(producto.getDescripcion())
                .imagen(producto.getImagen())
                .categoria(mapCategoriaToSimpleDTO(producto.getCategoria()))
                .disponible(producto.isDisponible())
                .activo(producto.isActivo())
                .build();
    }

    // Metodo auxiliar para mapear categoría (simplificado)
    private static CategoriaSimpleDTO mapCategoriaToSimpleDTO(Categoria categoria) {
        if (categoria == null) {
            return null;
        }

        return CategoriaSimpleDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .build();
    }

    public static Pedido toEntity(PedidoRegister pedidoCreate) {
        return Pedido.builder()
                .fecha(pedidoCreate.getFecha())
                .estado(Estado.valueOf(pedidoCreate.getEstado()))
                .total(pedidoCreate.getTotal())
                // El usuario se setea en el servicio
                .build();
    }

    public static void updateEntityFromEdit(Pedido pedido, PedidoEdit pedidoEdit) {
        if (pedidoEdit.getEstado() != null) {
            pedido.setEstado(Estado.valueOf(pedidoEdit.getEstado()));
        }
    }
}
