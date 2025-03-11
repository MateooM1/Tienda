package com.ejemplo.model;

// Clase Electronico que hereda de Producto
public class Electronico extends Producto {
    private String marca;
    private int garantia; // en meses

    public Electronico(int id, String nombre, double precio, String marca, int garantia) {
        super(id, nombre, precio); // Llama al constructor de Producto
        this.marca = marca;
        this.garantia = garantia;
    }

    public String getMarca() {
        return marca;
    }

    public int getGarantia() {
        return garantia;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setGarantia(int garantia) {
        this.garantia = garantia;
    }

    @Override
    public String toString() {
        return super.toString() + ", Electronico{marca='" + marca + "', garantia=" + garantia + " meses}";
    }
}
