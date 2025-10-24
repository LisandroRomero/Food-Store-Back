package com.example.foodstore.controller;

import com.example.foodstore.dto.request.DetallePedidoRegister;
import com.example.foodstore.dto.request.DetallePedidoEdit;
import com.example.foodstore.service.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/detalles-pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody DetallePedidoRegister detallePedidoCreate) {
        try {
            return ResponseEntity.ok().body(detallePedidoService.crear(detallePedidoCreate));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody DetallePedidoEdit detallePedidoEdit) {
        try {
            return ResponseEntity.ok().body(detallePedidoService.actualizar(id, detallePedidoEdit));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        try {
            detallePedidoService.eliminar(id);
            return ResponseEntity.ok().body("Detalle de pedido eliminado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> buscaTodos() {
        try {
            return ResponseEntity.ok().body(detallePedidoService.buscaTodos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscaId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(detallePedidoService.buscarId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<?> buscarPorPedido(@PathVariable Long pedidoId) {
        try {
            return ResponseEntity.ok(detallePedidoService.buscarPorPedido(pedidoId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }
}
