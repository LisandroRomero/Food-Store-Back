package com.example.foodstore.service;

import com.example.foodstore.dto.request.CarritoCompraRequest;
import com.example.foodstore.dto.request.PedidoRegister;
import com.example.foodstore.dto.request.PedidoEdit;
import com.example.foodstore.dto.response.PedidoResponseDTO;

import java.util.List;

public interface PedidoService {
    PedidoResponseDTO crear(PedidoRegister pedidoCreate);
    PedidoResponseDTO crearPedidoDesdeCarrito(CarritoCompraRequest carritoRequest);
    PedidoResponseDTO actualizar(Long id, PedidoEdit pedidoEdit);
    PedidoResponseDTO buscarId(Long id);
    List<PedidoResponseDTO> buscaTodos();
    void eliminar(Long id);
    List<PedidoResponseDTO> buscarPorUsuario(Long usuarioId);
    List<PedidoResponseDTO> buscarPorEstado(String estado);
}
