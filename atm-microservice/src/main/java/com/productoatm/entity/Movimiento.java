package com.productoatm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos")
@Getter
@Setter

public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;

    private String tipo;
    private BigDecimal importe;

    private LocalDateTime fecha;

    private String descripcion;

    public Movimiento(Cuenta cuenta, String tipo, BigDecimal importe, LocalDateTime fecha, String descripcion) {
        this.cuenta = cuenta;
        this.tipo = tipo;
        this.importe = importe;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }
}