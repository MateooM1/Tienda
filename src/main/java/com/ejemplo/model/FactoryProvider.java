package com.ejemplo.model;

public class FactoryProvider {
    public static FactoriaProducto getFactory(String tipoProducto) {
        if ("Alimento".equalsIgnoreCase(tipoProducto)) {
            return new factoriaAlimento();
        } else if ("Electronico".equalsIgnoreCase(tipoProducto)) {
            return new factoriaElectronico();
        }
        throw new IllegalArgumentException("Tipo de producto no válido: " + tipoProducto);
    }
}
