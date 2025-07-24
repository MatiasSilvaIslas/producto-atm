package com.productoatm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SaldoResponse {
    @JsonProperty("exito")
    private boolean exito;

    @JsonProperty("mensaje")
    private String mensaje;

    @JsonProperty("saldo")
    private BigDecimal saldo;
}