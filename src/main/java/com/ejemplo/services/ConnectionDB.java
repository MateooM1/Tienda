package com.ejemplo.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static ConnectionDB instancia;
    private Connection conexion;
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=Tienda;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "sa";
    private static final String PASSWORD = "$0nLine4Biz";

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
