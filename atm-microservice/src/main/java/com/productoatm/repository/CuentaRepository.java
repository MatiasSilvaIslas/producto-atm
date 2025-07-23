package com.productoatm.repository;

import com.productoatm.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, String> {
    Optional<Cuenta> findByNumero(String numeroCuenta);

    Optional<Cuenta> findByCbu(String cbu);
}