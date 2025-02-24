package com.ejemplo.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ejemplo.ConfigLoader;


public class ConnectionDB {

    private static ConnectionDB instancia;
    private Connection conexion;
    String URL = ConfigLoader.get("DB_URL");
    String USUARIO = ConfigLoader.get("DB_USER");
    String PASSWORD = ConfigLoader.get("DB_PASSWORD");


    private ConnectionDB() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            this.conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
        }
    }

    public static ConnectionDB getInstancia() {
        if (instancia == null) {
            instancia = new ConnectionDB();
        }
        return instancia;
    }

    // Método para obtener la conexión activa
    public Connection getConexion() {
        try {
            if (this.conexion == null || this.conexion.isClosed()) {
                this.conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(" Error al reconectar a SQL Server");
        }
        return this.conexion;
    }
    
    public void cerrarConexion() {
        try {
            if (this.conexion != null && !this.conexion.isClosed()) {
                this.conexion.close();
            }
        } catch (SQLException e) {
        }
    }

}
