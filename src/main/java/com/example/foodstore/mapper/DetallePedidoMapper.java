package com.example.foodstore.mapper;

import com.example.foodstore.dto.request.DetallePedidoRegister;
import com.example.foodstore.dto.request.DetallePedidoEdit;
import com.example.foodstore.dto.response.DetallePedidoResponseDTO;
import com.example.foodstore.entity.DetallePedido;

public class DetallePedidoMapper {

    public static DetallePedidoResponseDTO toDTO(DetallePedido detallePedido) {
        return DetallePedidoResponseDTO.builder()
                .id(detallePedido.getId())
                .cantidad(detallePedido.getCantidad())
                .subtotal(detallePedido.getSubtotal())
                .producto(ProductoMapper.toDTO(detallePedido.getProducto()))
                .build();
    }

    public static DetallePedido toEntity(DetallePedidoRegister detallePedidoCreate) {
        return DetallePedido.builder()
                .cantidad(detallePedidoCreate.getCantidad())
                .subtotal(detallePedidoCreate.getSubtotal())
                // El pedido y producto se setean en el servicio
                .build();
    }

    public static void updateEntityFromEdit(DetallePedido detallePedido, DetallePedidoEdit detallePedidoEdit) {
        detallePedido.setCantidad(detallePedidoEdit.getCantidad());
        detallePedido.setSubtotal(detallePedidoEdit.getSubtotal());
    }
}
