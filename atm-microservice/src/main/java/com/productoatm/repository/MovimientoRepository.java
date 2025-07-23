package com.productoatm.repository;

import com.productoatm.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaNumeroOrderByFechaDesc(String numeroCuenta);
}

