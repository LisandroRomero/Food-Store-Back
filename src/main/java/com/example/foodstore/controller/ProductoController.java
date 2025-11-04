package com.example.foodstore.controller;

import com.example.foodstore.dto.request.ProductoRegister;
import com.example.foodstore.dto.request.ProductoEdit;
import com.example.foodstore.dto.response.ProductoResponseDTO;
import com.example.foodstore.excepcion.DuplicateResourceException;
import com.example.foodstore.excepcion.ResourceNotFoundException;
import com.example.foodstore.service.ProductoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ProductoRegister productoRegister) {
        log.debug("POST /api/productos - Nombre: {}", productoRegister.getNombre());
        try {
            ProductoResponseDTO producto = productoService.crear(productoRegister);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of(
                        "message", "Producto creado exitosamente",
                        "data", producto
                    ));

        } catch (DuplicateResourceException e) {
            log.error("Recurso duplicado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", e.getMessage()));

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
            log.error("Error inesperado al crear producto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        log.debug("GET /api/productos/{} - ID: {}", id, id);
        try {
            ProductoResponseDTO producto = productoService.buscarPorId(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(producto);

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
     * Buscar todos los productos (admin)
     */
    @GetMapping
    public ResponseEntity<?> buscarTodos() {
        log.debug("GET /api/productos - Buscando todos los productos");
        try {
            List<ProductoResponseDTO> productos = productoService.buscarTodos();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(productos);

        } catch (Exception e) {
            log.error("Error interno del servidor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }

    /**
     * Buscar productos con paginación
     */
    @GetMapping("/paginas")
    public ResponseEntity<?> buscarConPaginacion(Pageable pageable) {
        log.debug("GET /api/productos/paginas - Página: {}, Tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<ProductoResponseDTO> productos = productoService.buscarConPaginacion(pageable);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(productos);

        } catch (Exception e) {
            log.error("Error interno del servidor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }

    /**
     * Buscar productos por categoría
     */
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<?> buscarPorCategoria(@PathVariable Long categoriaId) {
        log.debug("GET /api/productos/categoria/{} - CategoriaId: {}", categoriaId, categoriaId);
        try {
            List<ProductoResponseDTO> productos = productoService.buscarPorCategoria(categoriaId);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(productos);

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
     * Buscar productos disponibles 
     */
    @GetMapping("/disponibles")
    public ResponseEntity<?> buscarDisponibles() {
        log.debug("GET /api/productos/disponibles - Buscando productos disponibles");
        try {
            List<ProductoResponseDTO> productos = productoService.buscarDisponibles();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(productos);

        } catch (Exception e) {
            log.error("Error interno del servidor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }

    /**
     * Actualizar producto
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoEdit productoEdit) {
        log.debug("PUT /api/productos/{} - ID: {}", id, id);
        try {
            ProductoResponseDTO producto = productoService.actualizar(id, productoEdit);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of(
                        "message", "Producto actualizado exitosamente",
                        "data", producto
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
     * Cambiar disponibilidad de producto
     */
    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<?> cambiarDisponibilidad(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> disponibilidadData) {
        log.debug("PATCH /api/productos/{}/disponibilidad - ID: {}", id, id);
        try {
            Boolean disponible = disponibilidadData.get("disponible");
            if (disponible == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Map.of("message", "El campo 'disponible' es requerido"));
            }

            ProductoResponseDTO producto = productoService.cambiarDisponibilidad(id, disponible);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of(
                        "message", "Disponibilidad actualizada exitosamente",
                        "data", producto
                    ));

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
     * Eliminar producto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        log.debug("DELETE /api/productos/{} - ID: {}", id, id);
        try {
            productoService.eliminar(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Producto eliminado logicamente exitosamente"));

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
}