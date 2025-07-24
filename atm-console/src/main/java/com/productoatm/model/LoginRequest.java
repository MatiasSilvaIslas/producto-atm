package com.productoatm.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Pattern;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull
    @NotBlank(message = "El número de tarjeta es obligatorio")
    @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}-\\d{4}", message = "El número de tarjeta debe tener formato xxxx-xxxx-xxxx-xxxx")
    private String numeroTarjeta;
}
