package com.productoatm.repository;

import com.productoatm.entity.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TarjetaRepository extends JpaRepository<Tarjeta, String> {
    Optional<Tarjeta> findByNumero(String numero);
}