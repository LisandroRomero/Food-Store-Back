package com.example.foodstore.mapper;

import com.example.foodstore.dto.request.UsuarioRegister;
import com.example.foodstore.dto.request.UsuarioEdit;
import com.example.foodstore.dto.response.UsuarioResponseDTO;
import com.example.foodstore.entity.Usuario;
import com.example.foodstore.entity.Rol;
import com.example.foodstore.util.Sha256Util;

public class UsuarioMapper {

    // Convertir de Usuario a UsuarioResponseDTO
    public static UsuarioResponseDTO toDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .build();
    }

    // Convertir de UsuarioRegister a Usuario
    public static Usuario toEntity(UsuarioRegister usuarioRegister) {
        return Usuario.builder()
                .nombre(usuarioRegister.getNombre())
                .apellido(usuarioRegister.getApellido())
                .email(usuarioRegister.getEmail())
                .password(Sha256Util.hash(usuarioRegister.getPassword()))
                .rol(Rol.valueOf(usuarioRegister.getRol().toUpperCase()))
                .build();
    }

    // Actualizar Usuario desde UsuarioEdit 
    public static void updateFromDTO(Usuario usuario, UsuarioEdit usuarioEdit) {
        if (usuarioEdit.getNombre() != null) {
            usuario.setNombre(usuarioEdit.getNombre());
        }
        if (usuarioEdit.getApellido() != null) {
            usuario.setApellido(usuarioEdit.getApellido());
        }
        if (usuarioEdit.getEmail() != null) {
            usuario.setEmail(usuarioEdit.getEmail());
        }
        if (usuarioEdit.getPassword() != null) {
            usuario.setPassword(Sha256Util.hash(usuarioEdit.getPassword()));
        }
    }
}
