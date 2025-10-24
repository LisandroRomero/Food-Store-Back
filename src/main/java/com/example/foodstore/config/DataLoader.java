package com.example.foodstore.config;

import com.example.foodstore.entity.Usuario;
import com.example.foodstore.entity.Rol;
import com.example.foodstore.repository.UsuarioRepository;
import com.example.foodstore.util.Sha256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        
        System.out.println("Creando usuarios de prueba...");

        // Crear ADMIN
        Usuario admin = Usuario.builder()
                .nombre("Admin")
                .apellido("Sistema")
                .email("admin@food.com")
                .password(Sha256Util.hash("admin123"))
                .rol(Rol.ADMIN)
                .build();
        usuarioRepository.save(admin);
        System.out.println("Admin creado: admin@food.com / admin123");

        Usuario cliente = Usuario.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .email("juan@mail.com")
                .password(Sha256Util.hash("123456"))
                .rol(Rol.USUARIO)
                .build();
        usuarioRepository.save(cliente);
        System.out.println("Cliente creado: juan@mail.com / 123456");

        System.out.println("Usuarios de prueba creados exitosamente");
    }
}

