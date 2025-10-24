package com.example.foodstore.mapper;

import com.example.foodstore.dto.request.PedidoRegister;
import com.example.foodstore.dto.request.PedidoEdit;
import com.example.foodstore.dto.response.PedidoResponseDTO;
import com.example.foodstore.entity.Pedido;
import com.example.foodstore.entity.Estado;

public class PedidoMapper {

    public static PedidoResponseDTO toDTO(Pedido pedido) {
        return PedidoResponseDTO.builder()
                .id(pedido.getId())
                .fecha(pedido.getFecha())
                .estado(pedido.getEstado().name())
                .total(pedido.getTotal())
                .usuario(UsuarioMapper.toDTO(pedido.getUsuario()))
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
        pedido.setTotal(pedidoEdit.getTotal());
    }
}
