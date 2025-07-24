package com.productoatm.controller;

import com.productoatm.dto.LoginResponseDTO;
import com.productoatm.dto.OperacionResponseDTO;
import com.productoatm.dto.SaldoResponseDTO;
import com.productoatm.exception.*;
import com.productoatm.model.DepositoRequest;
import com.productoatm.model.ExtraccionRequest;
import com.productoatm.model.LoginRequest;
import com.productoatm.service.MovimientoService;
import com.productoatm.service.TarjetaService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
@Validated
@RestController
@RequestMapping("/api")
public class AtmController {

    private final TarjetaService tarjetaService;
    private final MovimientoService movimientoService;

    public AtmController(TarjetaService tarjetaService, MovimientoService movimientoService) {
        this.tarjetaService = tarjetaService;
        this.movimientoService = movimientoService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequest request) {
        boolean exito = tarjetaService.login(request.getNumeroTarjeta());
        return ResponseEntity.ok(new LoginResponseDTO(exito, exito ? "Ingreso exitoso" : "Ingreso no exitoso"));
    }

    @PostMapping("/extraer")
    public ResponseEntity<OperacionResponseDTO> extraer(@RequestBody @Valid ExtraccionRequest request) {
        try {
            movimientoService.extraer(request.getNumeroTarjeta(), request.getNumeroCuenta(), BigDecimal.valueOf(request.getImporte()));
            return ResponseEntity.ok(new OperacionResponseDTO(true, "Retire su dinero"));
        } catch (SaldoInsuficienteException | CuentaNoEncontradaException | TarjetaInactivaException |
                 TarjetaNoEncontradaException ex) {
            return ResponseEntity.badRequest().body(new OperacionResponseDTO(false, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new OperacionResponseDTO(false, "Error al procesar la extracción"));
        }
    }

    @PostMapping("/depositar")
    public ResponseEntity<OperacionResponseDTO> depositar(@RequestBody @Valid DepositoRequest request) {
        try {
            movimientoService.depositar(request.getNumeroTarjeta(), request.getCbu(), BigDecimal.valueOf(request.getImporte()));
            return ResponseEntity.ok(new OperacionResponseDTO(true, "Depósito exitoso"));
        } catch (CbuNoEncontradoException | TarjetaInactivaException | TarjetaNoEncontradaException | CuentaNoAsociadaATarjetaException ex) {
            return ResponseEntity.badRequest().body(new OperacionResponseDTO(false, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new OperacionResponseDTO(false, "Error al procesar el depósito"));
        }
    }

    @GetMapping("/saldo")
    public ResponseEntity<SaldoResponseDTO> consultarSaldo(
            @RequestParam
            @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}-\\d{4}", message = "Formato inválido para número de tarjeta")
            String numeroTarjeta,
            @RequestParam
            @Pattern(regexp = "CTA-\\d{4}", message = "Formato inválido para número de cuenta")
            String numeroCuenta) {
        try {
            BigDecimal saldo = movimientoService.consultarSaldo(numeroTarjeta, numeroCuenta);
            return ResponseEntity.ok(new SaldoResponseDTO(true, "Consulta exitosa", saldo));
        } catch (CuentaNoEncontradaException ex) {
            System.err.println("CuentaNoEncontradaException capturada: " + ex.getMessage());
            return ResponseEntity.badRequest().body(new SaldoResponseDTO(false, ex.getMessage(), null));
        } catch (TarjetaInactivaException ex) {
            System.err.println("TarjetaInactivaException capturada: " + ex.getMessage());
            return ResponseEntity.badRequest().body(new SaldoResponseDTO(false, ex.getMessage(), null));
        } catch (TarjetaNoEncontradaException ex) {
            System.err.println("TarjetaNoEncontradaException capturada: " + ex.getMessage());
            return ResponseEntity.badRequest().body(new SaldoResponseDTO(false, ex.getMessage(), null));
        } catch (CuentaNoAsociadaATarjetaException ex) {
            System.err.println("CuentaNoAsociadaATarjetaException capturada: " + ex.getMessage());
            return ResponseEntity.badRequest().body(new SaldoResponseDTO(false, ex.getMessage(), null));
        } catch (ConstraintViolationException ex) {
            System.err.println("ConstraintViolationException capturada: " + ex.getMessage());
            String mensaje = ex.getConstraintViolations().iterator().next().getMessage();
            return ResponseEntity.badRequest().body(new SaldoResponseDTO(false, mensaje, null));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new SaldoResponseDTO(false, "Error al consultar el saldo", null));
        }
    }

}
