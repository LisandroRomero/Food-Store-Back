package com.example.foodstore.service;

import com.example.foodstore.dto.request.UsuarioRegister;
import com.example.foodstore.dto.request.UsuarioEdit;
import com.example.foodstore.dto.request.UsuarioLoginDTO;
import com.example.foodstore.dto.response.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO registrar(UsuarioRegister usuarioRegister);
    UsuarioResponseDTO login(UsuarioLoginDTO loginDTO);
    UsuarioResponseDTO buscarPorId(Long id);
    UsuarioResponseDTO buscarPorEmail(String email);
    List<UsuarioResponseDTO> buscarTodos();
    UsuarioResponseDTO actualizar(Long id, UsuarioEdit usuarioEdit);
    void eliminar(Long id);
}
