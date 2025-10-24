package com.example.foodstore.excepcion;

// Excepción personalizada para manejar recursos duplicados
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
