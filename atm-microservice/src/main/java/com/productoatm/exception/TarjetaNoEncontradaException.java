package com.productoatm.exception;

public class TarjetaNoEncontradaException extends RuntimeException {

    public TarjetaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}