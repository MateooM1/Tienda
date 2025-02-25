package com.ejemplo.view;

import java.util.List;

import com.ejemplo.controller.ClienteController;
import com.ejemplo.model.Cliente;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClienteViewFX extends Application {

    private ClienteController clienteController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        clienteController = new ClienteController();

        Label title = new Label("Gestión de Clientes");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button btnAgregar = crearBoton("Agregar Cliente", e -> mostrarFormularioCliente("Agregar Cliente", "Ingrese los datos del cliente", null, null, -1));
        Button btnLeer = crearBoton("Ver Clientes", e -> mostrarClientes());
        Button btnActualizar = crearBoton("Actualizar Cliente", e -> mostrarActualizarCliente());
        Button btnEliminar = crearBoton("Eliminar Cliente", e -> mostrarEliminarCliente());

        VBox root = new VBox(15, title, btnAgregar, btnLeer, btnActualizar, btnEliminar);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f4f4f4;");

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Gestión de Clientes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button crearBoton(String texto, javafx.event.EventHandler<javafx.event.ActionEvent> evento) {
        Button btn = new Button(texto);
        btn.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-border-radius: 5;");
        btn.setOnAction(evento);
        return btn;
    }

    private void mostrarActualizarCliente() {
        int id = solicitarIdCliente("Actualizar Cliente");
        if (id != -1) {
            Cliente cliente = clienteController.obtenerClientePorId(id);
            if (cliente != null) {
                mostrarFormularioCliente("Actualizar Cliente", "Modifique los datos del cliente", cliente.getNombre(), cliente.getEmail(), id);
            }
        }
    }

    private void mostrarEliminarCliente() {
        int id = solicitarIdCliente("Eliminar Cliente");
        if (id != -1) {
            clienteController.eliminarCliente(id);
            mostrarMensaje("Cliente eliminado correctamente");
        }
    }

    private int solicitarIdCliente(String titulo) {
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle(titulo);
        idDialog.setHeaderText("Ingrese el ID del cliente.");
        idDialog.setContentText("ID del cliente:");

        return idDialog.showAndWait().map(idStr -> {
            try {
                return Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                mostrarMensaje("ID no válido.");
                return -1;
            }
        }).orElse(-1);
    }

    private void mostrarFormularioCliente(String titulo, String mensaje, String nombreInicial, String emailInicial, int id) {
        Stage stage = new Stage();
        stage.setTitle(titulo);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        Label lblNombre = new Label("Nombre:");
        TextField txtNombre = new TextField(nombreInicial != null ? nombreInicial : "");
        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField(emailInicial != null ? emailInicial : "");

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-padding: 10px;");
        btnGuardar.setOnAction(e -> {
            if (id == -1) {
                clienteController.agregarCliente(txtNombre.getText(), txtEmail.getText());
                mostrarMensaje("Cliente agregado correctamente");
            } else {
                clienteController.actualizarCliente(id, txtNombre.getText(), txtEmail.getText());
                mostrarMensaje("Cliente actualizado correctamente");
            }
            stage.close();
        });

        grid.add(lblNombre, 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(lblEmail, 0, 1);
        grid.add(txtEmail, 1, 1);
        grid.add(btnGuardar, 1, 2);

        Scene scene = new Scene(grid, 350, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarClientes() {
        List<Cliente> clientes = clienteController.obtenerClientes();
        StringBuilder sb = new StringBuilder();
        clientes.forEach(cliente -> sb.append(cliente).append("\n"));

        TextArea textArea = new TextArea(sb.toString());
        textArea.setEditable(false);

        Stage stage = new Stage();
        VBox vbox = new VBox(new Label("Clientes:"), textArea);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 400, 300);
        stage.setTitle("Lista de Clientes");
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Operación realizada");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
