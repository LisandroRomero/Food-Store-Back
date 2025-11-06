package com.example.foodstore.controller;

import com.example.foodstore.dto.request.UsuarioRegister;
import com.example.foodstore.dto.request.UsuarioEdit;
import com.example.foodstore.dto.request.UsuarioLoginDTO;
import com.example.foodstore.dto.response.UsuarioResponseDTO;
import com.example.foodstore.excepcion.DuplicateResourceException;
import com.example.foodstore.excepcion.ResourceNotFoundException;
import com.example.foodstore.excepcion.UnauthorizedException;
import com.example.foodstore.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("/register")
    public ResponseEntity<?> registrar(
        
    @Valid @RequestBody UsuarioRegister usuarioRegister) {
        log.debug("POST /api/usuarios/register - Email: {}", usuarioRegister.getEmail());
        try {
            UsuarioResponseDTO usuario = usuarioService.registrar(usuarioRegister);
            return ResponseEntity.ok(usuario);
        
        } catch (DuplicateResourceException e) { 
            log.error("Recurso duplicado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT) 
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", e.getMessage()));

        } catch (IllegalArgumentException e) {
            log.error("Error de validación: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", e.getMessage()));

        }catch (Exception e) {
            log.error("Error insperado en Registrar: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
        
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UsuarioLoginDTO loginDTO) {
        log.debug("POST /api/usuarios/login - Email: {}", loginDTO.getEmail());
        try {
         
            UsuarioResponseDTO usuario = usuarioService.login(loginDTO);
            return ResponseEntity.ok(usuario);
       
        } catch (UnauthorizedException e) {
            log.error("Acceso no autorizado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", e.getMessage()));
       
        } catch (Exception e) {
            log.error("Error insperado en login: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }
    
    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        log.debug("GET /api/usuarios/{id} - ID: {}", id);
        try {
            UsuarioResponseDTO usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (ResourceNotFoundException e) {
            log.error("Recurso no encontrado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            log.error("Error interno del servidor: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }
    
    // BUSCAR TODOS
    @GetMapping
    public ResponseEntity<?> buscarTodos() {
        log.debug("GET /api/usuarios - Buscando todos los usuarios");
        try {
            
            List<UsuarioResponseDTO> usuarios = usuarioService.buscarTodos();
            return ResponseEntity.ok(usuarios);
        
        } catch (Exception e) {
            log.error("Error interno del servidor: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }
    
    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioEdit usuarioEdit) {
        log.debug("PUT /api/usuarios/{id} - ID: {}", id);
        try {
            UsuarioResponseDTO usuario = usuarioService.actualizar(id, usuarioEdit);
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                    "message", "Usuario actualizado correctamente",
                    "data", usuario));
        } catch (ResourceNotFoundException e) {
            
            log.error("Recurso no encontrado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", e.getMessage()));

        }catch (DuplicateResourceException e) {
            
            log.error("Recurso duplicado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", e.getMessage()));

        } catch (IllegalArgumentException e) {
            log.error("Error de validación: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            
            log.error("Error interno del servidor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }
    
    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        log.debug("DELETE /api/usuarios/{id} - ID: {}", id);
        try {
            usuarioService.eliminar(id);
            return ResponseEntity.ok()
                .body(Map.of("message", "Usuario eliminado correctamente"));
        
        }catch (ResourceNotFoundException e) {
            log.error("Recurso no encontrado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", e.getMessage()));
        }
        catch (IllegalArgumentException e) {
            log.error("Error de validación: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", e.getMessage()));
        }
        catch (Exception e) {
            log.error("Error interno del servidor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }
}