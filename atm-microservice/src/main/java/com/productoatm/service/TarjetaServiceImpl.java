package com.productoatm.service;

import com.productoatm.entity.Tarjeta;
import com.productoatm.exception.TarjetaInactivaException;
import com.productoatm.exception.TarjetaNoEncontradaException;
import com.productoatm.repository.TarjetaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TarjetaServiceImpl implements TarjetaService {

    private static final Logger log = LoggerFactory.getLogger(TarjetaServiceImpl.class);

    private final TarjetaRepository tarjetaRepository;

    public TarjetaServiceImpl(TarjetaRepository tarjetaRepository) {
        this.tarjetaRepository = tarjetaRepository;
    }

    @Override
    public boolean login(String numeroTarjeta) {
        Tarjeta tarjeta = tarjetaRepository.findByNumero(numeroTarjeta)
                .orElseThrow(() -> new TarjetaNoEncontradaException("La tarjeta no fue encontrada"));

        if (!tarjeta.isActiva()) {
            log.warn("Login fallido: tarjeta {} está inactiva", numeroTarjeta);
            throw new TarjetaInactivaException("La tarjeta está inactiva");
        }

        log.info("Login exitoso para la tarjeta {}", numeroTarjeta);
        return true;
    }
}