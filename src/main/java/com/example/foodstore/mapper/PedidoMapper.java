package com.example.foodstore.mapper;

import com.example.foodstore.dto.PedidoCreate;
import com.example.foodstore.dto.PedidoDto;
import com.example.foodstore.dto.PedidoEdit;
import com.example.foodstore.entity.Pedido;
import com.example.foodstore.entity.Estado;

public class PedidoMapper {

    public static PedidoDto toDTO(Pedido pedido) {
        return PedidoDto.builder()
                .id(pedido.getId())
                .fecha(pedido.getFecha())
                .estado(pedido.getEstado().name())
                .total(pedido.getTotal())
                .usuario(UsuarioMapper.toDTO(pedido.getUsuario()))
                .build();
    }

    public static Pedido toEntity(PedidoCreate pedidoCreate) {
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
