package com.productoatm.service;

import java.math.BigDecimal;

public interface MovimientoService {
    void extraer(String numeroTarjeta, String numeroCuenta, BigDecimal monto);
    void depositar(String numeroTarjeta, String cbu, BigDecimal monto);
    BigDecimal consultarSaldo(String numeroTarjeta, String numeroCuenta);
}
