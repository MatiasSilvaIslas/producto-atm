package com.productoatm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExtraccionRequest {
    private String numeroTarjeta;
    private String numeroCuenta;
    private double importe;
}
