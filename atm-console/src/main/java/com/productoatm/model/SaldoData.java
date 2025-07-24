package com.productoatm.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaldoData {
    @NotNull(message = "El número de tarjeta es obligatorio")
    @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}-\\d{4}", message = "El número de tarjeta debe tener el formato xxxx-xxxx-xxxx-xxxx")
    private String numeroTarjeta;

    @NotNull(message = "El número de cuenta es obligatorio")
    @Pattern(regexp = "CTA-\\d{4}", message = "El número de cuenta debe tener formato CTA-0001")
    private String numeroCuenta;
}
