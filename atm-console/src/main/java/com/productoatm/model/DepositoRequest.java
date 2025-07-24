package com.productoatm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DepositoRequest {
    @NotNull(message = "El número de tarjeta es obligatorio")
    @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}-\\d{4}", message = "El número de tarjeta debe tener el formato xxxx-xxxx-xxxx-xxxx")
    private String numeroTarjeta;

    @NotNull(message = "El CBU es obligatorio")
    @Pattern(regexp = "CBU-\\d{4}", message = "El CBU debe tener formato CBU-0001")
    private String cbu;
    @NotNull(message = "El importe es obligatorio")
    @Positive(message = "El importe debe ser un valor positivo")
    private double importe;
}
