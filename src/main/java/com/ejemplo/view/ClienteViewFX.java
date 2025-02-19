package com.ejemplo.view;

import com.ejemplo.controller.ClienteController;
import com.ejemplo.model.Cliente;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ClienteViewFX extends Application {

    private ClienteController clienteController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        clienteController = new ClienteController();

        // Crear los botones CRUD
        Button btnAgregar = new Button("Agregar Cliente");
        btnAgregar.setOnAction(e -> mostrarAgregarCliente());

        Button btnLeer = new Button("Ver Clientes");
        btnLeer.setOnAction(e -> mostrarClientes());

        Button btnActualizar = new Button("Actualizar Cliente");
        btnActualizar.setOnAction(e -> mostrarActualizarCliente());

        Button btnEliminar = new Button("Eliminar Cliente");
        btnEliminar.setOnAction(e -> mostrarEliminarCliente());

        // Crear un VBox para contener los botones
        VBox root = new VBox(10);
        root.getChildren().addAll(btnAgregar, btnLeer, btnActualizar, btnEliminar);

        // Crear la escena y mostrarla
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Gestión de Clientes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Funcionalidad para agregar cliente
    private void mostrarAgregarCliente() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Agregar Cliente");
        dialog.setHeaderText("Ingrese el nombre y el email del cliente.");

        // Recoger los datos de nombre y email
        dialog.setContentText("Nombre del cliente:");
        dialog.showAndWait().ifPresent(nombre -> {
            TextInputDialog emailDialog = new TextInputDialog();
            emailDialog.setTitle("Agregar Cliente");
            emailDialog.setHeaderText("Ingrese el email del cliente.");
            emailDialog.setContentText("Email del cliente:");
            emailDialog.showAndWait().ifPresent(email -> {
                // Llamada al controlador para agregar el cliente
                clienteController.agregarCliente(nombre, email);
                mostrarMensaje("Cliente agregado correctamente");
            });
        });
    }

    // Funcionalidad para ver todos los clientes
    private void mostrarClientes() {
        List<Cliente> clientes = clienteController.obtenerClientes();
        StringBuilder sb = new StringBuilder();
        for (Cliente cliente : clientes) {
            sb.append(cliente).append("\n");
        }

        TextArea textArea = new TextArea(sb.toString());
        textArea.setEditable(false);

        // Mostrar los datos de los clientes
        Stage stage = new Stage();
        VBox vbox = new VBox(new Label("Clientes: "), textArea);
        Scene scene = new Scene(vbox, 400, 300);
        stage.setTitle("Lista de Clientes");
        stage.setScene(scene);
        stage.show();
    }

    // Funcionalidad para actualizar un cliente
    private void mostrarActualizarCliente() {
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Actualizar Cliente");
        idDialog.setHeaderText("Ingrese el ID del cliente a actualizar.");

        // Obtener el ID del cliente a actualizar
        idDialog.setContentText("ID del cliente:");
        idDialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                Cliente cliente = clienteController.obtenerClientePorId(id);
                if (cliente != null) {
                    TextInputDialog nombreDialog = new TextInputDialog(cliente.getNombre());
                    nombreDialog.setTitle("Actualizar Cliente");
                    nombreDialog.setHeaderText("Actualice el nombre del cliente.");
                    nombreDialog.setContentText("Nombre del cliente:");
                    nombreDialog.showAndWait().ifPresent(nombre -> {
                        TextInputDialog emailDialog = new TextInputDialog(cliente.getEmail());
                        emailDialog.setTitle("Actualizar Cliente");
                        emailDialog.setHeaderText("Actualice el email del cliente.");
                        emailDialog.setContentText("Email del cliente:");
                        emailDialog.showAndWait().ifPresent(email -> {
                            // Llamada al controlador para actualizar el cliente
                            clienteController.actualizarCliente(id, nombre, email);
                            mostrarMensaje("Cliente actualizado correctamente");
                        });
                    });
                } else {
                    mostrarMensaje("Cliente no encontrado.");
                }
            } catch (NumberFormatException e) {
                mostrarMensaje("ID no válido.");
            }
        });
    }

    // Funcionalidad para eliminar un cliente
    private void mostrarEliminarCliente() {
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Eliminar Cliente");
        idDialog.setHeaderText("Ingrese el ID del cliente a eliminar.");

        // Obtener el ID del cliente a eliminar
        idDialog.setContentText("ID del cliente:");
        idDialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                clienteController.eliminarCliente(id);
                mostrarMensaje("Cliente eliminado correctamente");
            } catch (NumberFormatException e) {
                mostrarMensaje("ID no válido.");
            }
        });
    }

    // Mostrar un mensaje en una ventana emergente
    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Operación realizada");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
