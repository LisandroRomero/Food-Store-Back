package com.example.foodstore.service;

import com.example.foodstore.dto.DetallePedidoCreate;
import com.example.foodstore.dto.DetallePedidoDto;
import com.example.foodstore.dto.DetallePedidoEdit;
import java.util.List;

public interface DetallePedidoService {
    DetallePedidoDto crear(DetallePedidoCreate detallePedidoCreate);
    DetallePedidoDto actualizar(Long id, DetallePedidoEdit detallePedidoEdit);
    DetallePedidoDto buscarId(Long id);
    List<DetallePedidoDto> buscaTodos();
    void eliminar(Long id);
    List<DetallePedidoDto> buscarPorPedido(Long pedidoId);
}
