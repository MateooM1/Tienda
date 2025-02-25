package com.ejemplo.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ejemplo.ConfigLoader;

public class ConnectionDB {

    private static ConnectionDB instancia;
    private Connection conexion;
    private final String URL;
    private final String USUARIO;
    private final String PASSWORD;

    // Constructor privado para Singleton
    private ConnectionDB() throws Exception {
        URL = ConfigLoader.get("DB_URL");
        USUARIO = ConfigLoader.get("DB_USER");
        PASSWORD = ConfigLoader.get("DB_PASSWORD");

        if (URL == null || USUARIO == null || PASSWORD == null) {
            throw new Exception(" Error: Parámetros de conexión no encontrados en config.properties");
        }

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new Exception("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    // Obtener la instancia Singleton
    public static ConnectionDB getInstancia() throws Exception {
        if (instancia == null) {
            instancia = new ConnectionDB();
        }
        return instancia;
    }

    // Obtener la conexión activa
    public Connection getConexion() throws Exception {
        if (this.conexion == null || this.conexion.isClosed()) {
            this.conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        }
        return this.conexion;
    }

    public void cerrarConexion() throws Exception {
        if (this.conexion != null && !this.conexion.isClosed()) {
            this.conexion.close();
        }
    }
}
