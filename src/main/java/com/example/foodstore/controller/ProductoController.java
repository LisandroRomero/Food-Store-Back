package com.example.foodstore.controller;

import com.example.foodstore.dto.request.ProductoRegister;
import com.example.foodstore.dto.request.ProductoEdit;
import com.example.foodstore.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ProductoRegister productoCreate) {
        try {
            return ResponseEntity.ok().body(productoService.crear(productoCreate));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ProductoEdit productoEdit) {
        try {
            return ResponseEntity.ok().body(productoService.actualizar(id, productoEdit));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        try {
            productoService.eliminar(id);
            return ResponseEntity.ok().body("Producto eliminado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> buscaTodos() {
        try {
            return ResponseEntity.ok().body(productoService.buscaTodos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscaId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productoService.buscarId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        try {
            return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }
}
