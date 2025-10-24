package com.example.foodstore.service;

import com.example.foodstore.dto.PedidoCreate;
import com.example.foodstore.dto.PedidoDto;
import com.example.foodstore.dto.PedidoEdit;
import java.util.List;

public interface PedidoService {
    PedidoDto crear(PedidoCreate pedidoCreate);
    PedidoDto actualizar(Long id, PedidoEdit pedidoEdit);
    PedidoDto buscarId(Long id);
    List<PedidoDto> buscaTodos();
    void eliminar(Long id);
    List<PedidoDto> buscarPorUsuario(Long usuarioId);
    List<PedidoDto> buscarPorEstado(String estado);
}
