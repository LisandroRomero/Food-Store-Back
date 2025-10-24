package com.example.foodstore.excepcion;

// Excepción personalizada para manejar recursos no encontrados
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
