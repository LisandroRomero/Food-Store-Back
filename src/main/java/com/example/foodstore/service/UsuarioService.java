package com.example.foodstore.service;

import com.example.foodstore.dto.UsuarioCreate;
import com.example.foodstore.dto.UsuarioDto;
import com.example.foodstore.dto.UsuarioEdit;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsuarioService {
    UsuarioDto crear(UsuarioCreate usuarioCreate);
    UsuarioDto actualizar(Long id, UsuarioEdit usuarioEdit);
    UsuarioDto buscarId(Long id);
    List<UsuarioDto> buscaTodos();
    void eliminar(Long id);
    UsuarioDto buscarPorEmail(String email);
    ResponseEntity<?> login(UsuarioDto usuarioDto);
}
