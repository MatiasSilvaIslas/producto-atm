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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequest request) {
        boolean exito = tarjetaService.login(request.getNumeroTarjeta());
        return ResponseEntity.ok(new LoginResponseDTO(exito, exito ? "Ingreso exitoso" : "Ingreso no exitoso"));
    }

    @PostMapping("/extraer")
    public ResponseEntity<OperacionResponseDTO> extraer(@RequestBody ExtraccionRequest request) {
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
    public ResponseEntity<OperacionResponseDTO> depositar(@RequestBody DepositoRequest request) {
        try {
            movimientoService.depositar(request.getNumeroTarjeta(), request.getCbu(), BigDecimal.valueOf(request.getImporte()));
            return ResponseEntity.ok(new OperacionResponseDTO(true, "Depósito exitoso"));
        } catch (CbuNoEncontradoException | TarjetaInactivaException | TarjetaNoEncontradaException ex) {
            return ResponseEntity.badRequest().body(new OperacionResponseDTO(false, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new OperacionResponseDTO(false, "Error al procesar el depósito"));
        }
    }

    @GetMapping("/saldo")
    public ResponseEntity<SaldoResponseDTO> consultarSaldo(@RequestParam String numeroTarjeta,
                                                           @RequestParam String numeroCuenta) {
        try {
            BigDecimal saldo = movimientoService.consultarSaldo(numeroTarjeta, numeroCuenta);
            return ResponseEntity.ok(new SaldoResponseDTO(true, "Consulta exitosa", saldo));
        } catch (CuentaNoEncontradaException | TarjetaInactivaException | TarjetaNoEncontradaException ex) {
            return ResponseEntity.badRequest().body(new SaldoResponseDTO(false, ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new SaldoResponseDTO(false, "Error al consultar el saldo", null));
        }
    }
}
