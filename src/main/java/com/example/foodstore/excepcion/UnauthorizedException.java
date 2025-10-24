package com.example.foodstore.excepcion;

// Excepción personalizada para manejar acceso no autorizado
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
    
}
