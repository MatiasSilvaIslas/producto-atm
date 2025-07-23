package com.productoatm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Tarjeta {
    @Id
    private String numero;

    @Column(nullable = false)
    private boolean activa;

    @OneToMany(mappedBy = "tarjeta", cascade = CascadeType.ALL)
    private List<TarjetaCuenta> tarjetaCuentas;
}