package com.example.foodstore.service.impl;

import com.example.foodstore.dto.request.UsuarioRegister;
import com.example.foodstore.dto.request.UsuarioEdit;
import com.example.foodstore.dto.request.UsuarioLoginDTO;
import com.example.foodstore.dto.response.UsuarioResponseDTO;
import com.example.foodstore.entity.Usuario;
import com.example.foodstore.mapper.UsuarioMapper;
import com.example.foodstore.repository.UsuarioRepository;
import com.example.foodstore.service.UsuarioService;
import com.example.foodstore.util.Sha256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioResponseDTO registrar(UsuarioRegister usuarioRegister) {
 
        if (usuarioRepository.findByEmail(usuarioRegister.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        
        // Convertir DTO a entidad y guardar
        Usuario usuario = UsuarioMapper.toEntity(usuarioRegister);
        usuario = usuarioRepository.save(usuario);
        
        return UsuarioMapper.toDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO login(UsuarioLoginDTO loginDTO) {
        // Buscar usuario por email
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));
        
        // Verificar password
        String passwordHash = Sha256Util.hash(loginDTO.getPassword());
        if (!usuario.getPassword().equals(passwordHash)) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }
        
        return UsuarioMapper.toDTO(usuario);
    }

    // Buscar por ID
    @Override
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        return UsuarioMapper.toDTO(usuario);
    }

    // Buscar por email
    @Override
    public UsuarioResponseDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        return UsuarioMapper.toDTO(usuario);
    }

    // Buscar todos
    @Override
    public List<UsuarioResponseDTO> buscarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Actualizar usuario
    @Override
    public UsuarioResponseDTO actualizar(Long id, UsuarioEdit usuarioEdit) {
        // Buscar usuario
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        // Verificar si el email ya existe (si se está cambiando)
        if (usuarioEdit.getEmail() != null && !usuarioEdit.getEmail().equals(usuario.getEmail())) {
            if (usuarioRepository.findByEmail(usuarioEdit.getEmail()).isPresent()) {
                throw new IllegalArgumentException("El email ya está en uso");
            }
        }
        
        // Actualizar campos
        UsuarioMapper.updateFromDTO(usuario, usuarioEdit);
        usuario = usuarioRepository.save(usuario);
        
        return UsuarioMapper.toDTO(usuario);
    }

    // Eliminar usuario
    @Override
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
