package com.productoatm.client;

import com.productoatm.model.DepositoRequest;
import com.productoatm.model.ExtraccionRequest;
import com.productoatm.model.LoginRequest;
import com.productoatm.util.Config;
import com.productoatm.util.Endpoints;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AtmClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = Config.get("api.base.url");

    public void login(String tarjeta) {
        LoginRequest request = new LoginRequest(tarjeta);
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + Endpoints.LOGIN, request, String.class);
        System.out.println(response.getBody());
    }

    public void extraer(String tarjeta, String cuenta, double importe) {
        ExtraccionRequest request = new ExtraccionRequest(tarjeta, cuenta, importe);
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + Endpoints.EXTRAER, request, String.class);
        System.out.println(response.getBody());
    }

    public void depositar(String tarjeta, String cbu, double importe) {
        DepositoRequest request = new DepositoRequest(tarjeta, cbu, importe);
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + Endpoints.DEPOSITAR, request, String.class);
        System.out.println(response.getBody());
    }

    public void consultarSaldo(String tarjeta, String cuenta) {
        String url = String.format("%s%s?tarjeta=%s&cuenta=%s", baseUrl, Endpoints.SALDO, tarjeta, cuenta);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println(response.getBody());
    }
}
