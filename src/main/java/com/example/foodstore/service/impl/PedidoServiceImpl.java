package com.example.foodstore.service.impl;

import com.example.foodstore.dto.PedidoCreate;
import com.example.foodstore.dto.PedidoDto;
import com.example.foodstore.dto.PedidoEdit;
import com.example.foodstore.entity.Pedido;
import com.example.foodstore.entity.Usuario;
import com.example.foodstore.entity.Estado;
import com.example.foodstore.mapper.PedidoMapper;
import com.example.foodstore.repository.PedidoRepository;
import com.example.foodstore.repository.UsuarioRepository;
import com.example.foodstore.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public PedidoDto crear(PedidoCreate pedidoCreate) {
        Pedido pedido = PedidoMapper.toEntity(pedidoCreate);

        // Buscar y asignar el usuario
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(pedidoCreate.getUsuarioId());
        if (usuarioOptional.isPresent() && !usuarioOptional.get().isEliminado()) {
            pedido.setUsuario(usuarioOptional.get());
            pedido = pedidoRepository.save(pedido);
            return PedidoMapper.toDTO(pedido);
        }
        return null;
    }

    @Override
    public PedidoDto actualizar(Long id, PedidoEdit pedidoEdit) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            PedidoMapper.updateEntityFromEdit(pedido, pedidoEdit);
            pedido = pedidoRepository.save(pedido);
            return PedidoMapper.toDTO(pedido);
        }
        return null;
    }

    @Override
    public PedidoDto buscarId(Long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            if (!pedidoOptional.get().isEliminado()) {
                return PedidoMapper.toDTO(pedidoOptional.get());
            }
        }
        return null;
    }

    @Override
    public List<PedidoDto> buscaTodos() {
        List<Pedido> pedidos = pedidoRepository.findAllByEliminadoFalse();
        return pedidos.stream()
                .map(PedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            pedido.setEliminado(true);
            pedidoRepository.save(pedido);
        }
    }

    @Override
    public List<PedidoDto> buscarPorUsuario(Long usuarioId) {
        List<Pedido> pedidos = pedidoRepository.findByUsuarioIdAndEliminadoFalse(usuarioId);
        return pedidos.stream()
                .map(PedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoDto> buscarPorEstado(String estado) {
        List<Pedido> pedidos = pedidoRepository.findByEstadoAndEliminadoFalse(Estado.valueOf(estado));
        return pedidos.stream()
                .map(PedidoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
