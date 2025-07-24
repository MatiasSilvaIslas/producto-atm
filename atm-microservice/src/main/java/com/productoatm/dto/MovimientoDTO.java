package com.productoatm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovimientoDTO {
    private Long id;
    private String numeroCuenta;
    private String cbuCuenta;
    private String tipo;
    private BigDecimal importe;
    private LocalDateTime fecha;
    private String descripcion;

    public MovimientoDTO(com.productoatm.entity.Movimiento movimiento) {
        this.id = movimiento.getId();
        this.numeroCuenta = movimiento.getCuenta().getNumero();
        this.cbuCuenta = movimiento.getCuenta().getCbu();
        this.tipo = movimiento.getTipo();
        this.importe = movimiento.getImporte();
        this.fecha = movimiento.getFecha();
        this.descripcion = movimiento.getDescripcion();
    }
}