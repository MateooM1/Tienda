package com.ejemplo.view;

import javafx.scene.control.Alert;

public class ErrorHandler {
    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Aplicación");
        alert.setHeaderText("Se ha producido un error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
