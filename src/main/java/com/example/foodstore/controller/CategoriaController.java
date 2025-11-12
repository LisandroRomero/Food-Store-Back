package com.example.foodstore.controller;

import com.example.foodstore.dto.request.CategoriaRegister;
import com.example.foodstore.dto.request.CategoriaEdit;
import com.example.foodstore.dto.response.CategoriaResponseDTO;
import com.example.foodstore.excepcion.DuplicateResourceException;
import com.example.foodstore.excepcion.ResourceNotFoundException;
import com.example.foodstore.service.CategoriaService;

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
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    /**
     * Crear nueva categoría
     */
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CategoriaRegister categoriaRegister) {
        log.debug("POST /api/categorias - Nombre: {}", categoriaRegister.getNombre());
        try {
            CategoriaResponseDTO categoria = categoriaService.crear(categoriaRegister);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of(
                        "message", "Categoría creada exitosamente",
                        "data", categoria
                    ));

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

        } catch (Exception e) {
            log.error("Error inesperado al crear categoría: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        log.debug("GET /api/categorias/{} - ID: {}", id, id);
        try {
            CategoriaResponseDTO categoria = categoriaService.buscarPorId(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(categoria);

        } catch (ResourceNotFoundException e) {
            log.error("Recurso no encontrado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", e.getMessage()));

        } catch (Exception e) {
            log.error("Error interno del servidor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }

    /**
     * Buscar todas las categorías (admin)
     */
    @GetMapping
    public ResponseEntity<?> buscarTodas() {
        log.debug("GET /api/categorias - Buscando todas las categorías");
        try {
            List<CategoriaResponseDTO> categorias = categoriaService.buscarTodas();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(categorias);

        } catch (Exception e) {
            log.error("Error interno del servidor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }

    /**
     * Buscar categorías con productos disponibles (tienda)
     * Esta funcion se utiliza en el front para mostrar las categorias que un usuario puede ver.
     */
    @GetMapping("/disponibles")
    public ResponseEntity<?> buscarConProductosDisponibles() {
        log.debug("GET /api/categorias/disponibles - Buscando categorías con productos disponibles");
        try {
            List<CategoriaResponseDTO> categorias = categoriaService.buscarConProductosDisponibles();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(categorias);

        } catch (Exception e) {
            log.error("Error interno del servidor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }

    /**
     * Actualizar categoría
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaEdit categoriaEdit) {
        log.debug("PUT /api/categorias/{} - ID: {}", id, id);
        try {
            CategoriaResponseDTO categoria = categoriaService.actualizar(id, categoriaEdit);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of(
                        "message", "Categoría actualizada exitosamente",
                        "data", categoria
                    ));

        } catch (ResourceNotFoundException e) {
            log.error("Recurso no encontrado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", e.getMessage()));

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

        } catch (Exception e) {
            log.error("Error interno del servidor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }

    /**
     * Eliminar categoría
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        log.debug("DELETE /api/categorias/{} - ID: {}", id, id);
        try {
            categoriaService.eliminar(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Categoría eliminada exitosamente"));

        } catch (ResourceNotFoundException e) {
            log.error("Recurso no encontrado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
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
    @PutMapping("/{id}/restaurar")
    public ResponseEntity<?> restaurar(@PathVariable Long id) {
        categoriaService.restaurar(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Buscar categorías raíz (sin padre)
     */
    @GetMapping("/raiz")
    public ResponseEntity<?> buscarCategoriasRaiz() {
        log.debug("GET /api/categorias/raiz - Buscando categorías raíz");
        try {
            List<CategoriaResponseDTO> categorias = categoriaService.buscarCategoriasRaiz();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(categorias);

        } catch (Exception e) {
            log.error("Error interno del servidor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }

    /**
     * Buscar árbol completo de categorías
     */
    @GetMapping("/arbol")
    public ResponseEntity<?> buscarArbolCategorias() {
        log.debug("GET /api/categorias/arbol - Buscando árbol de categorías");
        try {
            List<CategoriaResponseDTO> categorias = categoriaService.buscarArbolCategorias();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(categorias);

        } catch (Exception e) {
            log.error("Error interno del servidor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }

    /**
     * Buscar subcategorías por categoría padre
     */
    @GetMapping("/{categoriaPadreId}/subcategorias")
    public ResponseEntity<?> buscarSubcategorias(@PathVariable Long categoriaPadreId) {
        log.debug("GET /api/categorias/{}/subcategorias - Buscando subcategorías", categoriaPadreId);
        try {
            List<CategoriaResponseDTO> subcategorias = categoriaService.buscarSubcategorias(categoriaPadreId);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(subcategorias);

        } catch (ResourceNotFoundException e) {
            log.error("Recurso no encontrado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", e.getMessage()));

        } catch (Exception e) {
            log.error("Error interno del servidor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }

    /**
     * Mover categoría a otro padre
     */
    @PatchMapping("/{categoriaId}/mover")
    public ResponseEntity<?> moverCategoria(
            @PathVariable Long categoriaId,
            @RequestBody Map<String, Long> request) {

        log.debug("PATCH /api/categorias/{}/mover - Moviendo categoría", categoriaId);
        try {
            Long nuevoPadreId = request.get("nuevoPadreId"); // Puede ser null para hacerla raíz

            CategoriaResponseDTO categoria = categoriaService.moverCategoria(categoriaId, nuevoPadreId);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of(
                            "message", "Categoría movida exitosamente",
                            "data", categoria
                    ));

        } catch (ResourceNotFoundException e) {
            log.error("Recurso no encontrado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
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
}