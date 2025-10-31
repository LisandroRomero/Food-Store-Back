package com.example.foodstore.service.impl;

import com.example.foodstore.dto.request.PedidoRegister;
import com.example.foodstore.dto.request.PedidoEdit;
import com.example.foodstore.dto.response.PedidoResponseDTO;
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
    public PedidoResponseDTO crear(PedidoRegister pedidoCreate) {
        Pedido pedido = PedidoMapper.toEntity(pedidoCreate);

        // Buscar y asignar el usuario
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(pedidoCreate.getUsuarioId());
        if (usuarioOptional.isPresent()) {
            pedido.setUsuario(usuarioOptional.get());
            pedido = pedidoRepository.save(pedido);
            return PedidoMapper.toDTO(pedido);
        }
        return null;
    }

    @Override
    public PedidoResponseDTO actualizar(Long id, PedidoEdit pedidoEdit) {
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
    public PedidoResponseDTO buscarId(Long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            return PedidoMapper.toDTO(pedidoOptional.get());
        }
        return null;
    }

    @Override
    public List<PedidoResponseDTO> buscaTodos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(PedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
        }
    }

    @Override
    public List<PedidoResponseDTO> buscarPorUsuario(Long usuarioId) {
        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(usuarioId);
        return pedidos.stream()
                .map(PedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoResponseDTO> buscarPorEstado(String estado) {
        List<Pedido> pedidos = pedidoRepository.findByEstado(Estado.valueOf(estado));
        return pedidos.stream()
                .map(PedidoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
