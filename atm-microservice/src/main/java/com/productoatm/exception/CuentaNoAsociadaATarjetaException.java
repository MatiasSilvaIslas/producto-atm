package com.productoatm.exception;

public class CuentaNoAsociadaATarjetaException extends RuntimeException {
    public CuentaNoAsociadaATarjetaException(String message) {
        super(message);
    }
}