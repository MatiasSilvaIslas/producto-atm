package com.productoatm.controller;

import com.productoatm.entity.Movimiento;
import com.productoatm.repository.MovimientoRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private final MovimientoRepository movimientoRepository;

    public MovimientoController(MovimientoRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

    @Operation(summary = "Obtener todos los movimientos")
    @GetMapping
    public List<Movimiento> obtenerTodos() {
        return movimientoRepository.findAll();
    }

    @Operation(summary = "Obtener movimientos por número de cuenta")
    @GetMapping("/cuenta/{numeroCuenta}")
    public List<Movimiento> obtenerPorCuenta(@PathVariable String numeroCuenta) {
        return movimientoRepository.findByCuentaNumeroOrderByFechaDesc(numeroCuenta);
    }
}