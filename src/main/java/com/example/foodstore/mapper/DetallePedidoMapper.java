package com.example.foodstore.mapper;

import com.example.foodstore.dto.request.DetallePedidoRegister;
import com.example.foodstore.dto.request.DetallePedidoEdit;
import com.example.foodstore.dto.response.DetallePedidoResponseDTO;
import com.example.foodstore.entity.DetallePedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DetallePedidoMapper {

    @Autowired
    private static ProductoMapper productoMapper;

    public DetallePedidoResponseDTO toResponseDTO(DetallePedido detallePedido) {
        if (detallePedido == null) {
            return null;
        }
        
        return DetallePedidoResponseDTO.builder()
                .id(detallePedido.getId())
                .cantidad(detallePedido.getCantidad())
                .subtotal(detallePedido.getSubtotal())
                .producto(productoMapper.toResponseDTO(detallePedido.getProducto()))
                .build();
    }

    public DetallePedido toEntity(DetallePedidoRegister detallePedidoCreate) {
        if (detallePedidoCreate == null) {
            return null;
        }
        
        return DetallePedido.builder()
                .cantidad(detallePedidoCreate.getCantidad())
                .subtotal(detallePedidoCreate.getSubtotal())
                // El pedido y producto se setean en el servicio
                .build();
    }

    public void updateEntityFromEdit(DetallePedido detallePedido, DetallePedidoEdit detallePedidoEdit) {
        if (detallePedido == null || detallePedidoEdit == null) {
            return;
        }
        
        if (detallePedidoEdit.getCantidad() != null) {
            detallePedido.setCantidad(detallePedidoEdit.getCantidad());
        }
        
        if (detallePedidoEdit.getSubtotal() != null) {
            detallePedido.setSubtotal(detallePedidoEdit.getSubtotal());
        }
    }
}
