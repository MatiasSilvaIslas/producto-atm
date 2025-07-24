package com.productoatm.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productoatm.model.*;
import com.productoatm.util.Config;
import com.productoatm.util.Endpoints;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class AtmClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = Config.get("api.base.url");
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void login(String tarjeta) {
        try {
            LoginRequest request = new LoginRequest(tarjeta);
            ResponseEntity<OperacionResponse> response = restTemplate.postForEntity(
                    baseUrl + Endpoints.LOGIN, request, OperacionResponse.class
            );
            System.out.println(response.getBody().getMensaje());
        } catch (HttpClientErrorException e) {
            try {
                String responseBody = e.getResponseBodyAsString();
                OperacionResponse errorResponse = objectMapper.readValue(responseBody, OperacionResponse.class);
                System.out.println(errorResponse.getMensaje());
            } catch (Exception parseException) {
                System.out.println("Ingreso no exitoso");
            }
        } catch (Exception e) {
            System.out.println("Error inesperado durante el login.");
        }
    }

    public void extraer(String tarjeta, String cuenta, double importe) {
        try {
            ExtraccionRequest request = new ExtraccionRequest(tarjeta, cuenta, importe);
            ResponseEntity<OperacionResponse> response = restTemplate.postForEntity(
                    baseUrl + Endpoints.EXTRAER, request, OperacionResponse.class
            );
            System.out.println(response.getBody().getMensaje());
        } catch (HttpClientErrorException e) {
            try {
                String responseBody = e.getResponseBodyAsString();
                OperacionResponse errorResponse = objectMapper.readValue(responseBody, OperacionResponse.class);
                System.out.println(errorResponse.getMensaje());
            } catch (Exception parseException) {
                System.out.println("No se pudo realizar la extracción.");
            }
        } catch (Exception e) {
            System.out.println("No se pudo realizar la extracción.");
        }
    }

    public void depositar(String tarjeta, String cbu, double importe) {
        try {
            DepositoRequest request = new DepositoRequest(tarjeta, cbu, importe);
            ResponseEntity<OperacionResponse> response = restTemplate.postForEntity(
                    baseUrl + Endpoints.DEPOSITAR, request, OperacionResponse.class
            );
            System.out.println(response.getBody().getMensaje());
        } catch (HttpClientErrorException e) {
            try {
                String responseBody = e.getResponseBodyAsString();
                OperacionResponse errorResponse = objectMapper.readValue(responseBody, OperacionResponse.class);
                System.out.println(errorResponse.getMensaje());
            } catch (Exception parseException) {
                System.out.println("No se pudo realizar el depósito.");
            }
        } catch (Exception e) {
            System.out.println("No se pudo realizar el depósito.");
        }
    }

    public void consultarSaldo(String tarjeta, String cuenta) {
        try {
            String url = String.format("%s%s?numeroTarjeta=%s&numeroCuenta=%s",
                    baseUrl, Endpoints.SALDO, tarjeta, cuenta);
            ResponseEntity<SaldoResponse> response = restTemplate.getForEntity(url, SaldoResponse.class);
            SaldoResponse saldoResponse = response.getBody();

            if (saldoResponse != null && saldoResponse.isExito()) {
                System.out.printf("Su saldo es $ %.2f%n", saldoResponse.getSaldo());
            } else {
                System.out.println("Error al consultar saldo.");
            }
        } catch (HttpClientErrorException e) {
            try {
                String responseBody = e.getResponseBodyAsString();
                SaldoResponse errorResponse = objectMapper.readValue(responseBody, SaldoResponse.class);
                System.out.println(errorResponse.getMensaje());
            } catch (Exception parseException) {
                System.out.println("Error al consultar saldo.");
            }
        } catch (Exception e) {
            System.out.println("Error al consultar saldo.");
        }
    }
}
