package com.productoatm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class GenericResponse<T> {
    private String estado;
    private String mensaje;
    private String timestamp;
    private T data;
}
