package com.productoatm.repository;

import com.productoatm.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, String> {
    Optional<Cuenta> findByNumero(String numeroCuenta);

    Optional<Cuenta> findByCbu(String cbu);


    @Query("SELECT CASE WHEN COUNT(tc) > 0 THEN true ELSE false END " +
            "FROM TarjetaCuenta tc " +
            "WHERE tc.cuenta.cbu = :cbu AND tc.tarjeta.numero = :numeroTarjeta")
    boolean existsByCbuAndTarjetaNumero(@Param("cbu") String cbu,
                                        @Param("numeroTarjeta") String numeroTarjeta);
}