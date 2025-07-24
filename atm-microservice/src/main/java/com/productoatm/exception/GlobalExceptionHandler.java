package com.productoatm.exception;

import com.productoatm.dto.LoginResponseDTO;
import com.productoatm.dto.OperacionResponseDTO;
import com.productoatm.dto.SaldoResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<OperacionResponseDTO> handleSaldoInsuficiente(SaldoInsuficienteException ex) {
        return ResponseEntity.badRequest().body(new OperacionResponseDTO(false, ex.getMessage()));
    }

    @ExceptionHandler(CuentaNoEncontradaException.class)
    public ResponseEntity<?> handleCuentaNoEncontrada(CuentaNoEncontradaException ex) {
        return ResponseEntity.badRequest().body(new OperacionResponseDTO(false, ex.getMessage()));
    }

    @ExceptionHandler(TarjetaInactivaException.class)
    public ResponseEntity<LoginResponseDTO> handleTarjetaInactiva(TarjetaInactivaException ex) {
        return ResponseEntity.status(401).body(new LoginResponseDTO(false, ex.getMessage()));
    }

    @ExceptionHandler(CbuNoEncontradoException.class)
    public ResponseEntity<OperacionResponseDTO> handleCbuNoEncontrado(CbuNoEncontradoException ex) {
        return ResponseEntity.badRequest().body(new OperacionResponseDTO(false, ex.getMessage()));
    }

    @ExceptionHandler(MontoInvalidoException.class)
    public ResponseEntity<OperacionResponseDTO> handleMontoInvalido(MontoInvalidoException ex) {
        return ResponseEntity.badRequest().body(new OperacionResponseDTO(false, ex.getMessage()));
    }

    @ExceptionHandler(TarjetaNoEncontradaException.class)
    public ResponseEntity<LoginResponseDTO> handleTarjetaNoEncontrada(TarjetaNoEncontradaException ex) {
        return ResponseEntity.status(404).body(new LoginResponseDTO(false, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<OperacionResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Datos inválidos");
        return ResponseEntity.badRequest().body(new OperacionResponseDTO(false, errorMessage));
    }

    @ExceptionHandler(CuentaNoAsociadaATarjetaException.class)
    public ResponseEntity<SaldoResponseDTO> handleCuentaNoAsociada(CuentaNoAsociadaATarjetaException ex) {
        return ResponseEntity.badRequest().body(new SaldoResponseDTO(false, ex.getMessage(), null));
    }

    // Handler genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<OperacionResponseDTO> handleGeneral(Exception ex) {
        return ResponseEntity.internalServerError().body(
                new OperacionResponseDTO(false, "Ocurrió un error inesperado")
        );
    }
}
