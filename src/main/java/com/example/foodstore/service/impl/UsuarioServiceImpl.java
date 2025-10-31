package com.example.foodstore.service.impl;

import com.example.foodstore.dto.request.UsuarioRegister;
import com.example.foodstore.dto.request.UsuarioEdit;
import com.example.foodstore.dto.request.UsuarioLoginDTO;
import com.example.foodstore.dto.response.UsuarioResponseDTO;
import com.example.foodstore.entity.Usuario;
import com.example.foodstore.excepcion.DuplicateResourceException;
import com.example.foodstore.excepcion.ResourceNotFoundException;
import com.example.foodstore.excepcion.UnauthorizedException;
import com.example.foodstore.mapper.UsuarioMapper;
import com.example.foodstore.repository.UsuarioRepository;
import com.example.foodstore.service.UsuarioService;
import com.example.foodstore.util.Sha256Util;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioResponseDTO registrar(UsuarioRegister usuarioRegister) {
        log.debug("intentando registrar usuario: {}", usuarioRegister.getEmail());

        if (usuarioRepository.findByEmail(usuarioRegister.getEmail()).isPresent()) {
            log.warn("Intento de registro con email ya registrado: {}", usuarioRegister.getEmail());
            throw new DuplicateResourceException("El email ya está registrado"); 
        }
        
        Usuario usuario = UsuarioMapper.toEntity(usuarioRegister);
        usuario = usuarioRepository.save(usuario);
        
        log.info("usuario registrado exitosamente con id: {}", usuario.getId());

        return UsuarioMapper.toDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO login(UsuarioLoginDTO loginDTO) {
        log.debug("intentando iniciar sesión para email: {}", loginDTO.getEmail());
        try {

            if (!usuarioRepository.existsByEmail(loginDTO.getEmail())) {
                throw new UnauthorizedException("Correo electrónico Inválido");
            }
            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginDTO.getEmail());
            
            if (!usuarioOpt.get().getPassword().equals(Sha256Util.hash(loginDTO.getPassword()))) {
                throw new UnauthorizedException("Contraseña Incorrecta");
            }
            
            log.info("inicio de sesión exitoso para id: {}", usuarioOpt.get().getId());
            
            return UsuarioMapper.toDTO(usuarioOpt.get());
        }catch (Exception e) {
            log.error("Error insperado en login: {}", e.getMessage());
            throw e;
        }
    }

    // Buscar por ID
    @Override
    public UsuarioResponseDTO buscarPorId(Long id) {
        log.debug("intentando buscar usuario por id: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con id: {}", id);
                    return new ResourceNotFoundException("Usuario no encontrado");
                });
        
        log.info("usuario encontrado con id: {}", usuario.getId());
        return UsuarioMapper.toDTO(usuario);
    }

    // Buscar por email
    @Override
    public UsuarioResponseDTO buscarPorEmail(String email) {
        log.debug("intentando buscar usuario por email: {}", email);
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con email: {}", email);
                    return new ResourceNotFoundException("Usuario no encontrado");
                });
        
        log.info("usuario encontrado con email: {}", usuario.getEmail());
        return UsuarioMapper.toDTO(usuario);
    }

    // Buscar todos
    @Override
    public List<UsuarioResponseDTO> buscarTodos() {
        log.debug("intentando buscar todos los usuarios");
        List<Usuario> usuarios = usuarioRepository.findAll();
        log.info("{} usuarios encontrados", usuarios.size());
        return usuarios.stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Actualizar usuario
    @Override
    public UsuarioResponseDTO actualizar(Long id, UsuarioEdit usuarioEdit) {
        
        log.debug("intentando actualizar usuario con id: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con id: {}", id);
                    return new ResourceNotFoundException("Usuario no encontrado");
                });
        
        if (usuarioEdit.getEmail() != null && !usuarioEdit.getEmail().equals(usuario.getEmail())) {
            if (usuarioRepository.findByEmail(usuarioEdit.getEmail()).isPresent()) {
                throw new DuplicateResourceException("El email ya está en uso");
            }
        }
        
        log.debug("actualizando campos del usuario");
        UsuarioMapper.updateFromDTO(usuario, usuarioEdit);
        usuario = usuarioRepository.save(usuario);
        
        log.info("usuario actualizado exitosamente con id: {}", usuario.getId());
        return UsuarioMapper.toDTO(usuario);
    }

    // Eliminar usuario
    @Override
    public void eliminar(Long id) {
        log.debug("intentando eliminar usuario con id: {}", id);
        if (!usuarioRepository.existsById(id)) {
            log.warn("Usuario no encontrado con id: {}", id);
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
        log.info("usuario eliminado exitosamente con id: {}", id);
    }
}
