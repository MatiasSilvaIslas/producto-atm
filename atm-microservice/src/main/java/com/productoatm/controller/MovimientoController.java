package com.productoatm.controller;

import com.productoatm.dto.MovimientoDTO;
import com.productoatm.dto.OperacionResponseDTO;
import com.productoatm.entity.Movimiento;
import com.productoatm.exception.CuentaNoEncontradaException;
import com.productoatm.repository.CuentaRepository;
import com.productoatm.repository.MovimientoRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movimientos")
@Validated
public class MovimientoController {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    public MovimientoController(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Operation(summary = "Obtener todos los movimientos")
    @GetMapping
    public List<MovimientoDTO> obtenerTodos() {
        return movimientoRepository.findAll()
                .stream()
                .map(MovimientoDTO::new)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener movimientos por número de cuenta")
    @GetMapping("/cuenta/{numeroCuenta}")
    public ResponseEntity<?> obtenerPorCuenta(
            @PathVariable
            @Pattern(regexp = "CTA-\\d{4}", message = "Formato inválido para número de cuenta")
            String numeroCuenta) {
        try {
            cuentaRepository.findByNumero(numeroCuenta)
                    .orElseThrow(() -> new CuentaNoEncontradaException("Cuenta no encontrada"));

            List<MovimientoDTO> movimientos = movimientoRepository.findByCuentaNumeroOrderByFechaDesc(numeroCuenta)
                    .stream()
                    .map(MovimientoDTO::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(movimientos);
        } catch (CuentaNoEncontradaException ex) {
            return ResponseEntity.badRequest().body(new OperacionResponseDTO(false, ex.getMessage()));
        } catch (ConstraintViolationException ex) {
            String mensaje = ex.getConstraintViolations().iterator().next().getMessage();
            return ResponseEntity.badRequest().body(new OperacionResponseDTO(false, mensaje));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body(new OperacionResponseDTO(false, "Error al obtener movimientos"));
        }
    }
}
