package com.productoatm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DepositoRequest {
    private String numeroTarjeta;
    private String cbuDestino;
    private double importe;
}
