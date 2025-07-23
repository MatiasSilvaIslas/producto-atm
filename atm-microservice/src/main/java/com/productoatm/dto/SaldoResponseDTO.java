package com.productoatm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SaldoResponseDTO {
    private boolean exito;
    private String mensaje;
    private BigDecimal saldo;
}
