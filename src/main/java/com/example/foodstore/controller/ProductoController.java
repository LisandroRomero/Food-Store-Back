package com.example.foodstore.controller;

import com.example.foodstore.dto.request.ProductoRegister;
import com.example.foodstore.dto.request.ProductoEdit;
import com.example.foodstore.dto.response.ProductoResponseDTO;
import com.example.foodstore.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Crear producto
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ProductoRegister productoCreate) {
        try {
            return ResponseEntity.ok(productoService.crear(productoCreate));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ProductoEdit productoEdit) {
        try {
            return ResponseEntity.ok(productoService.actualizar(id, productoEdit));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    // Eliminar (soft-delete) producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        try {
            productoService.eliminar(id);
            return ResponseEntity.ok("Producto eliminado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    // Restaurar producto
    @PutMapping("/{id}/restaurar")
    public ResponseEntity<Void> restaurar(@PathVariable Long id) {
        productoService.restaurar(id);
        return ResponseEntity.noContent().build();
    }

    // Listar productos activos
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listarActivos() {
        return ResponseEntity.ok(productoService.buscaTodos());
    }

    // Listar todos los productos (admin)
    @GetMapping("/admin")
    public ResponseEntity<List<ProductoResponseDTO>> listarTodosAdmin() {
        return ResponseEntity.ok(productoService.buscaTodosAdmin());
    }

    // Obtener producto por id
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> buscarPorId(@PathVariable Long id) {
        ProductoResponseDTO dto = productoService.buscarId(id);
        if (dto != null) return ResponseEntity.ok(dto);
        return ResponseEntity.notFound().build();
    }

    // Buscar por nombre
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        try {
            return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    // Filtrar productos por categoría, disponibilidad y stock
    @GetMapping("/filtrar")
    public ResponseEntity<List<ProductoResponseDTO>> filtrar(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) Boolean disponible,
            @RequestParam(required = false) Boolean conStock) {

        List<ProductoResponseDTO> productos = productoService.filtrarProductos(categoriaId, disponible, conStock);
        return ResponseEntity.ok(productos);
    }
}

