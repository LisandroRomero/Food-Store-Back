package com.example.foodstore.config;

import com.example.foodstore.entity.Usuario;
import com.example.foodstore.entity.Rol;
import com.example.foodstore.repository.UsuarioRepository;
import com.example.foodstore.util.Sha256Util;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        
        System.out.println("Verificando y creando usuarios de prueba...");

        // Crear ADMIN solo si no existe
        if (!usuarioRepository.existsByEmail("admin@food.com")) {
            log.info("Creando admin...");
            Usuario admin = Usuario.builder()
                    .nombre("Admin")
                    .apellido("Sistema")
                    .email("admin@food.com")
                    .password(Sha256Util.hash("admin123"))
                    .rol(Rol.ADMIN)
                    .build();
            usuarioRepository.save(admin);
            log.info("Admin creado: admin@food.com / admin123");
        } else {
            log.info("Admin ya existe en la base de datos");
        }

        // Crear CLIENTE solo si no existe
        if (!usuarioRepository.existsByEmail("juan@mail.com")) {
            log.info("Creando cliente...");
            Usuario cliente = Usuario.builder()
                    .nombre("Juan")
                    .apellido("Pérez")
                    .email("juan@mail.com")
                    .password(Sha256Util.hash("123456"))
                    .rol(Rol.USUARIO)
                    .build();
            usuarioRepository.save(cliente);
            log.info("Cliente creado: juan@mail.com / 123456");
        } else {
            log.info("Cliente ya existe en la base de datos");
        }

        log.info("Verificación de usuarios de prueba completada");
    }
}

