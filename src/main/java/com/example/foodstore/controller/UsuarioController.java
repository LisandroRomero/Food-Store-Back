package com.example.foodstore.controller;

import com.example.foodstore.dto.UsuarioCreate;
import com.example.foodstore.dto.UsuarioEdit;
import com.example.foodstore.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody UsuarioCreate usuarioCreate) {
        try {
            return ResponseEntity.ok().body(usuarioService.crear(usuarioCreate));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody UsuarioEdit usuarioEdit) {
        try {
            return ResponseEntity.ok().body(usuarioService.actualizar(id, usuarioEdit));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        try {
            usuarioService.eliminar(id);
            return ResponseEntity.ok().body("Usuario eliminado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> buscaTodos() {
        try {
            return ResponseEntity.ok().body(usuarioService.buscaTodos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscaId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.buscarId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> buscaPorEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }
}
