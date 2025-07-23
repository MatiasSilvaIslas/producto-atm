package com.productoatm.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarjetaCuentaId {
    private String tarjetaNumero;
    private String cuentaNumero;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TarjetaCuentaId)) return false;
        TarjetaCuentaId that = (TarjetaCuentaId) o;
        return Objects.equals(tarjetaNumero, that.tarjetaNumero) &&
                Objects.equals(cuentaNumero, that.cuentaNumero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tarjetaNumero, cuentaNumero);
    }
}
