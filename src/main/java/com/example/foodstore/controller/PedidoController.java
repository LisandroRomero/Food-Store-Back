package com.example.foodstore.controller;

import com.example.foodstore.dto.PedidoCreate;
import com.example.foodstore.dto.PedidoEdit;
import com.example.foodstore.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody PedidoCreate pedidoCreate) {
        try {
            return ResponseEntity.ok().body(pedidoService.crear(pedidoCreate));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody PedidoEdit pedidoEdit) {
        try {
            return ResponseEntity.ok().body(pedidoService.actualizar(id, pedidoEdit));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        try {
            pedidoService.eliminar(id);
            return ResponseEntity.ok().body("Pedido eliminado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> buscaTodos() {
        try {
            return ResponseEntity.ok().body(pedidoService.buscaTodos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscaId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pedidoService.buscarId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> buscarPorUsuario(@PathVariable Long usuarioId) {
        try {
            return ResponseEntity.ok(pedidoService.buscarPorUsuario(usuarioId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> buscarPorEstado(@PathVariable String estado) {
        try {
            return ResponseEntity.ok(pedidoService.buscarPorEstado(estado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }
}
