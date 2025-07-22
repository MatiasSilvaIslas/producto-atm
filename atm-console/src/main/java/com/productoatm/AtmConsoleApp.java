package com.productoatm;
import com.productoatm.client.AtmClient;

public class AtmConsoleApp {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Debe ingresar una operación (login, extraer, depositar, saldo)");
            return;
        }

        AtmClient client = new AtmClient();
        String operacion = args[0];

        switch (operacion.toLowerCase()) {
            case "login":
                if (args.length != 2) {
                    System.out.println("Uso: login <tarjeta>");
                    return;
                }
                client.login(args[1]);
                break;

            case "extraer":
                if (args.length != 4) {
                    System.out.println("Uso: extraer <tarjeta> <cuenta> <importe>");
                    return;
                }
                client.extraer(args[1], args[2], Double.parseDouble(args[3]));
                break;

            case "depositar":
                if (args.length != 4) {
                    System.out.println("Uso: depositar <tarjeta> <cbu> <importe>");
                    return;
                }
                client.depositar(args[1], args[2], Double.parseDouble(args[3]));
                break;

            case "saldo":
                if (args.length != 3) {
                    System.out.println("Uso: saldo <tarjeta> <cuenta>");
                    return;
                }
                client.consultarSaldo(args[1], args[2]);
                break;

            default:
                System.out.println("Operación no reconocida.");
        }
    }
}
