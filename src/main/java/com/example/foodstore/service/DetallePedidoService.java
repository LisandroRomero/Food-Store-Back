package com.example.foodstore.service;

import com.example.foodstore.dto.request.DetallePedidoRegister;
import com.example.foodstore.dto.request.DetallePedidoEdit;
import com.example.foodstore.dto.response.DetallePedidoResponseDTO;

import java.util.List;

public interface DetallePedidoService {
    DetallePedidoResponseDTO crear(DetallePedidoRegister detallePedidoCreate);
    DetallePedidoResponseDTO actualizar(Long id, DetallePedidoEdit detallePedidoEdit);
    DetallePedidoResponseDTO buscarId(Long id);
    List<DetallePedidoResponseDTO> buscaTodos();
    void eliminar(Long id);
    List<DetallePedidoResponseDTO> buscarPorPedido(Long pedidoId);
}
