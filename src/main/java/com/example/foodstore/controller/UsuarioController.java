package com.example.foodstore.controller;

import com.example.foodstore.dto.request.UsuarioRegister;
import com.example.foodstore.dto.request.UsuarioEdit;
import com.example.foodstore.dto.request.UsuarioLoginDTO;
import com.example.foodstore.dto.response.UsuarioResponseDTO;
import com.example.foodstore.service.UsuarioService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("/register")
    public ResponseEntity<?> registrar(
            @Valid @RequestBody UsuarioRegister usuarioRegister) {
        try {
            UsuarioResponseDTO usuario = usuarioService.registrar(usuarioRegister);
            return ResponseEntity.status(201).body(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UsuarioLoginDTO loginDTO) {
        try {
            UsuarioResponseDTO usuario = usuarioService.login(loginDTO);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", e.getMessage()));
        }
    }
    
    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        try {
            UsuarioResponseDTO usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // BUSCAR TODOS
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> buscarTodos() {
        List<UsuarioResponseDTO> usuarios = usuarioService.buscarTodos();
        return ResponseEntity.ok(usuarios);
    }
    
    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioEdit usuarioEdit) {
        try {
            UsuarioResponseDTO usuario = usuarioService.actualizar(id, usuarioEdit);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}