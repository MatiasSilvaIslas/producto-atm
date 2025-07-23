package com.productoatm.service;

import com.productoatm.entity.Cuenta;
import com.productoatm.entity.Movimiento;
import com.productoatm.entity.Tarjeta;
import com.productoatm.exception.*;
import com.productoatm.repository.CuentaRepository;
import com.productoatm.repository.MovimientoRepository;
import com.productoatm.repository.TarjetaRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.productoatm.entity.TarjetaCuenta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private static final Logger log = LoggerFactory.getLogger(MovimientoServiceImpl.class);

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final TarjetaRepository tarjetaRepository;

    public MovimientoServiceImpl(CuentaRepository cuentaRepository,
                                 MovimientoRepository movimientoRepository,
                                 TarjetaRepository tarjetaRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
        this.tarjetaRepository = tarjetaRepository;
    }

    @Override
    @Transactional
    public void extraer(String numeroTarjeta, String numeroCuenta, BigDecimal monto) {
        // Validar tarjeta
        Tarjeta tarjeta = tarjetaRepository.findByNumero(numeroTarjeta)
                .orElseThrow(() -> new TarjetaNoEncontradaException("Tarjeta no encontrada"));

        if (!tarjeta.isActiva()) {
            throw new TarjetaInactivaException("Tarjeta inactiva");
        }

        // Validar cuenta pertenece a la tarjeta
        boolean cuentaValida = tarjeta.getTarjetaCuentas().stream()
                .map(TarjetaCuenta::getCuenta)
                .anyMatch(c -> c.getNumero().equals(numeroCuenta));

        if (!cuentaValida) {
            throw new CuentaNoEncontradaException("Cuenta no pertenece a la tarjeta");
        }

        // Validar saldo suficiente y hacer extracción
        Cuenta cuenta = cuentaRepository.findByNumero(numeroCuenta)
                .orElseThrow(() -> new CuentaNoEncontradaException("Cuenta no encontrada"));

        if (cuenta.getSaldo().compareTo(monto) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }

        cuenta.setSaldo(cuenta.getSaldo().subtract(monto));
        cuentaRepository.save(cuenta);

        Movimiento movimiento = new Movimiento(cuenta, "EXTRACCION", monto, LocalDateTime.now(), "Extracción por cajero automático");
        movimientoRepository.save(movimiento);

        log.info("Extracción de {} realizada en cuenta {}", monto, numeroCuenta);
    }


    @Override
    @Transactional
    public void depositar(String numeroTarjeta, String cbu, BigDecimal monto) {
        // Validar tarjeta
        Tarjeta tarjeta = tarjetaRepository.findByNumero(numeroTarjeta)
                .orElseThrow(() -> new TarjetaNoEncontradaException("Tarjeta no encontrada"));

        if (!tarjeta.isActiva()) {
            throw new TarjetaInactivaException("Tarjeta inactiva");
        }

        // Validar que la cuenta exista (puede no pertenecer a la tarjeta)
        Cuenta cuenta = cuentaRepository.findByCbu(cbu)
                .orElseThrow(() -> new CbuNoEncontradoException("CBU no encontrado"));

        cuenta.setSaldo(cuenta.getSaldo().add(monto));
        cuentaRepository.save(cuenta);

        Movimiento movimiento = new Movimiento(cuenta, "DEPOSITO", monto, LocalDateTime.now(), "Depósito en línea");
        movimientoRepository.save(movimiento);

        log.info("Depósito de {} realizado en cuenta {}", monto, cuenta.getNumero());
    }

    @Override
    @Transactional
    public BigDecimal consultarSaldo(String numeroTarjeta, String numeroCuenta) {
        Tarjeta tarjeta = tarjetaRepository.findByNumero(numeroTarjeta)
                .orElseThrow(() -> new TarjetaNoEncontradaException("Tarjeta no encontrada"));

        if (!tarjeta.isActiva()) {
            throw new TarjetaInactivaException("Tarjeta inactiva");
        }

        boolean cuentaValida = tarjeta.getTarjetaCuentas().stream()
                .map(TarjetaCuenta::getCuenta)
                .anyMatch(c -> c.getNumero().equals(numeroCuenta));

        if (!cuentaValida) {
            throw new CuentaNoEncontradaException("Cuenta no pertenece a la tarjeta");
        }

        Cuenta cuenta = cuentaRepository.findByNumero(numeroCuenta)
                .orElseThrow(() -> new CuentaNoEncontradaException("Cuenta no encontrada"));

        log.info("Consulta de saldo realizada para cuenta {}", numeroCuenta);

        return cuenta.getSaldo();
    }


}