package com.example.foodstore.service.impl;

import com.example.foodstore.dto.UsuarioCreate;
import com.example.foodstore.dto.UsuarioDto;
import com.example.foodstore.dto.UsuarioEdit;
import com.example.foodstore.entity.Usuario;
import com.example.foodstore.mapper.UsuarioMapper;
import com.example.foodstore.repository.UsuarioRepository;
import com.example.foodstore.service.UsuarioService;
import com.example.foodstore.util.Sha256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDto crear(UsuarioCreate usuarioCreate) {
        Usuario usuario = UsuarioMapper.toEntity(usuarioCreate);
        usuario = usuarioRepository.save(usuario);
        return UsuarioMapper.toDTO(usuario);
    }

    @Override
    public UsuarioDto actualizar(Long id, UsuarioEdit usuarioEdit) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            UsuarioMapper.updateEntityFromEdit(usuario, usuarioEdit);
            usuario = usuarioRepository.save(usuario);
            return UsuarioMapper.toDTO(usuario);
        }
        return null;
    }

    @Override
    public UsuarioDto buscarId(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            if (!usuarioOptional.get().isEliminado()) {
                return UsuarioMapper.toDTO(usuarioOptional.get());
            }
        }
        return null;
    }

    @Override
    public List<UsuarioDto> buscaTodos() {
        List<Usuario> usuarios = usuarioRepository.findAllByEliminadoFalse();
        return usuarios.stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setEliminado(true);
            usuarioRepository.save(usuario);
        }
    }

    @Override
    public UsuarioDto buscarPorEmail(String email) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByMail(email);
        if (usuarioOptional.isPresent() && !usuarioOptional.get().isEliminado()) {
            return UsuarioMapper.toDTO(usuarioOptional.get());
        }
        return null;
    }

    @Override
    public ResponseEntity<?> login(UsuarioDto usuarioDto) {
        // Remplazar 'ResponseEntity' por 'Excepciones'
        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findByMail(usuarioDto.getMail());

            if (usuarioOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Credenciales inválidas");
            }

            Usuario usuario = usuarioOptional.get();

            if (usuario.isEliminado()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Credenciales inválidas");
            }

            String passwordHash = Sha256Util.hash(usuarioDto.getPassword());

            if (usuario.getPassword().equals(passwordHash)) {
                UsuarioDto usuarioResponse = UsuarioMapper.toDTO(usuario);
                return ResponseEntity.ok(usuarioResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Credenciales inválidas");
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Ocurrió un error: " + e.getMessage());
        }
    }
}
