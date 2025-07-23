package com.productoatm.exception;

public class CbuNoEncontradoException extends RuntimeException {
    public CbuNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
