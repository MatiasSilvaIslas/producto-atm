package com.productoatm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Cuenta {
    @Id
    private String numero;

    private String cbu;

    private BigDecimal saldo;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL)
    private List<TarjetaCuenta> tarjetaCuentas;
}
