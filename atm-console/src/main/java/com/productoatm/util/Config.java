package com.productoatm.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer application.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
