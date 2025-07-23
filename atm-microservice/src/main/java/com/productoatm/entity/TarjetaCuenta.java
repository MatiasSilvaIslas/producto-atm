package com.productoatm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TarjetaCuenta {
    @EmbeddedId
    private TarjetaCuentaId id;

    @ManyToOne
    @MapsId("tarjetaNumero")
    @JoinColumn(name = "tarjeta_numero")
    private Tarjeta tarjeta;

    @ManyToOne
    @MapsId("cuentaNumero")
    @JoinColumn(name = "cuenta_numero")
    private Cuenta cuenta;

}
