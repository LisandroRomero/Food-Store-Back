package com.example.foodstore.controller;

import com.example.foodstore.dto.request.CategoriaEdit;
import com.example.foodstore.dto.request.CategoriaRegister;
import com.example.foodstore.dto.response.CategoriaResponseDTO;
import com.example.foodstore.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // Crear categoría
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CategoriaRegister categoriaCreate) {
        try {
            return ResponseEntity.ok(categoriaService.crear(categoriaCreate));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Listar categorías activas
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarActivas() {
        return ResponseEntity.ok(categoriaService.buscaTodos());
    }

    // Listar todas las categorías (admin)
    @GetMapping("/admin")
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodasAdmin() {
        return ResponseEntity.ok(categoriaService.buscaTodosAdmin());
    }

    // Obtener categoría por id
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {
        CategoriaResponseDTO dto = categoriaService.buscarId(id);
        if (dto != null) return ResponseEntity.ok(dto);
        return ResponseEntity.notFound().build();
    }

    // Actualizar categoría
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody CategoriaEdit categoriaEdit) {
        return ResponseEntity.ok(categoriaService.actualizar(id, categoriaEdit));
    }

    // Eliminar (soft-delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.ok("Categoría eliminada");
    }

    // Restaurar categoría
    @PutMapping("/{id}/restaurar")
    public ResponseEntity<?> restaurar(@PathVariable Long id) {
        categoriaService.restaurar(id);
        return ResponseEntity.noContent().build();
    }
}
