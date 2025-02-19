package com.ejemplo.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {

    private int id;
    private StringProperty nombre;
    private StringProperty email;

    // Constructor
    public Cliente(int id, String nombre, String email) {
        this.id = id;
        this.nombre = new SimpleStringProperty(nombre);
        this.email = new SimpleStringProperty(email);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nombre=" + nombre.get() + ", email=" + email.get() + "}";
    }
}
