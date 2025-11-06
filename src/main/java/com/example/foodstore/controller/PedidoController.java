package com.example.foodstore.controller;

import com.example.foodstore.dto.request.CarritoCompraRequest;
import com.example.foodstore.dto.request.PedidoRegister;
import com.example.foodstore.dto.request.PedidoEdit;
import com.example.foodstore.dto.response.PedidoResponseDTO;
import com.example.foodstore.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody PedidoRegister pedidoCreate) {
        try {
            return ResponseEntity.ok().body(pedidoService.crear(pedidoCreate));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }

    @PostMapping("/carrito")
    public ResponseEntity<?> crearPedidoDesdeCarrito(@Valid @RequestBody CarritoCompraRequest carritoRequest) {
        try {
            // Validar que hay items en el carrito
            if (carritoRequest.getItems() == null || carritoRequest.getItems().isEmpty()) {
                return ResponseEntity.badRequest().body("El carrito no puede estar vacío");
            }

            PedidoResponseDTO pedidoCreado = pedidoService.crearPedidoDesdeCarrito(carritoRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCreado);

        } catch (RuntimeException e) {
            // Capturar excepciones de negocio (stock insuficiente, producto no encontrado, etc.)
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Capturar cualquier otra excepción inesperada
            return ResponseEntity.internalServerError().body("Error interno al procesar el pedido");
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

    @GetMapping("/{id}")
    public ResponseEntity<?> buscaId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pedidoService.buscarId(id));
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        try {
            pedidoService.eliminar(id);
            return ResponseEntity.ok().body("Pedido eliminado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocurrió un error: " + e.getMessage());
        }
    }
}
