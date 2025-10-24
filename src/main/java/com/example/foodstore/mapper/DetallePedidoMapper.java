package com.example.foodstore.mapper;

import com.example.foodstore.dto.DetallePedidoCreate;
import com.example.foodstore.dto.DetallePedidoDto;
import com.example.foodstore.dto.DetallePedidoEdit;
import com.example.foodstore.entity.DetallePedido;

public class DetallePedidoMapper {

    public static DetallePedidoDto toDTO(DetallePedido detallePedido) {
        return DetallePedidoDto.builder()
                .id(detallePedido.getId())
                .cantidad(detallePedido.getCantidad())
                .subtotal(detallePedido.getSubtotal())
                .pedido(PedidoMapper.toDTO(detallePedido.getPedido()))
                .producto(ProductoMapper.toDTO(detallePedido.getProducto()))
                .build();
    }

    public static DetallePedido toEntity(DetallePedidoCreate detallePedidoCreate) {
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
