package com.productoatm.exception;

public class TarjetaInactivaException extends RuntimeException {
    public TarjetaInactivaException(String mensaje) {
        super(mensaje);
    }
}