package com.productoatm.model;

import lombok.Getter;
import lombok.Setter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OperacionResponse {
    @JsonProperty("exito")
    private boolean exito;

    @JsonProperty("mensaje")
    private String mensaje;
}