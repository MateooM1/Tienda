package com.ejemplo.model;

// Clase Alimento que hereda de Producto
public class Alimento extends Producto {
    private String fechaExpiracion;
    private double peso; // en kg

    public Alimento(int id, String nombre, double precio, String fechaExpiracion, double peso) {
        super(id, nombre, precio); // Llama al constructor de Producto
        this.fechaExpiracion = fechaExpiracion;
        this.peso = peso;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public double getPeso() {
        return peso;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return super.toString() + ", Alimento{fechaExpiracion='" + fechaExpiracion + "', peso=" + peso + " kg}";
    }
}
