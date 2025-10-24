package com.example.foodstore.mapper;

import com.example.foodstore.dto.UsuarioCreate;
import com.example.foodstore.dto.UsuarioDto;
import com.example.foodstore.dto.UsuarioEdit;
import com.example.foodstore.entity.Usuario;
import com.example.foodstore.entity.Rol;
import com.example.foodstore.util.Sha256Util;

public class UsuarioMapper {

    public static UsuarioDto toDTO(Usuario usuario) {
        return UsuarioDto.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .mail(usuario.getMail())
                .rol(usuario.getRol().name())
                .build();
    }

    public static Usuario toEntity(UsuarioCreate usuarioCreate) {
        return Usuario.builder()
                .nombre(usuarioCreate.getNombre())
                .apellido(usuarioCreate.getApellido())
                .mail(usuarioCreate.getMail())
                .password(Sha256Util.hash(usuarioCreate.getPassword()))
                .rol(Rol.valueOf(usuarioCreate.getRol()))
                .build();
    }

    public static void updateEntityFromEdit(Usuario usuario, UsuarioEdit usuarioEdit) {
        if (usuarioEdit.getNombre() != null) {
            usuario.setNombre(usuarioEdit.getNombre());
        }
        if (usuarioEdit.getApellido() != null) {
            usuario.setApellido(usuarioEdit.getApellido());
        }
        if (usuarioEdit.getPassword() != null) {
            usuario.setPassword(Sha256Util.hash(usuarioEdit.getPassword()));
        }
    }
}
