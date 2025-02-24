package com.ejemplo.view;

import java.util.List;

import com.ejemplo.controller.ProductoController;
import com.ejemplo.model.Producto;

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

public class ProductoViewFX extends Application {

    private ProductoController productoController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        productoController = new ProductoController();

        Label title = new Label("Gestión de Productos");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button btnAgregar = crearBoton("Agregar Producto", e -> mostrarAgregarProducto());
        Button btnLeer = crearBoton("Ver Productos", e -> mostrarProductos());
        Button btnActualizar = crearBoton("Actualizar Producto", e -> mostrarActualizarProducto());
        Button btnEliminar = crearBoton("Eliminar Producto", e -> mostrarEliminarProducto());

        VBox root = new VBox(15, title, btnAgregar, btnLeer, btnActualizar, btnEliminar);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f4f4f4;");

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Gestión de Productos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button crearBoton(String texto, javafx.event.EventHandler<javafx.event.ActionEvent> evento) {
        Button btn = new Button(texto);
        btn.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-border-radius: 5;");
        btn.setOnAction(evento);
        return btn;
    }

    private void mostrarAgregarProducto() {
        mostrarFormularioProducto("Agregar Producto", "Ingrese el nombre y precio del producto", null, 0.0);
    }

    private void mostrarActualizarProducto() {
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Actualizar Producto");
        idDialog.setHeaderText("Ingrese el ID del producto a actualizar.");
        idDialog.setContentText("ID del producto:");

        idDialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                Producto producto = productoController.obtenerProductoPorId(id);
                if (producto != null) {
                    mostrarFormularioProducto("Actualizar Producto", "Modifique los datos del producto", producto.getNombre(), producto.getPrecio(), id);
                } else {
                    mostrarMensaje("Producto no encontrado.");
                }
            } catch (NumberFormatException e) {
                mostrarMensaje("ID no válido.");
            }
        });
    }

    private void mostrarFormularioProducto(String titulo, String mensaje, String nombreInicial, double precioInicial) {
        mostrarFormularioProducto(titulo, mensaje, nombreInicial, precioInicial, -1);
    }

    private void mostrarFormularioProducto(String titulo, String mensaje, String nombreInicial, double precioInicial, int id) {
        Stage stage = new Stage();
        stage.setTitle(titulo);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        Label lblNombre = new Label("Nombre:");
        TextField txtNombre = new TextField(nombreInicial != null ? nombreInicial : "");
        Label lblPrecio = new Label("Precio:");
        TextField txtPrecio = new TextField(precioInicial > 0 ? String.valueOf(precioInicial) : "");

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-padding: 10px;");
        btnGuardar.setOnAction(e -> {
            if (id == -1) {
                productoController.agregarProducto(txtNombre.getText(), Double.parseDouble(txtPrecio.getText()));
                mostrarMensaje("Producto agregado correctamente");
            } else {
                productoController.actualizarProducto(id, txtNombre.getText(), Double.parseDouble(txtPrecio.getText()));
                mostrarMensaje("Producto actualizado correctamente");
            }
            stage.close();
        });

        grid.add(lblNombre, 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(lblPrecio, 0, 1);
        grid.add(txtPrecio, 1, 1);
        grid.add(btnGuardar, 1, 2);

        Scene scene = new Scene(grid, 350, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarProductos() {
        List<Producto> productos = productoController.obtenerProductos();
        StringBuilder sb = new StringBuilder();
        for (Producto producto : productos) {
            sb.append(producto).append("\n");
        }

        TextArea textArea = new TextArea(sb.toString());
        textArea.setEditable(false);

        Stage stage = new Stage();
        VBox vbox = new VBox(new Label("Productos:"), textArea);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 400, 300);
        stage.setTitle("Lista de Productos");
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarEliminarProducto() {
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Eliminar Producto");
        idDialog.setHeaderText("Ingrese el ID del producto a eliminar.");
        idDialog.setContentText("ID del producto:");

        idDialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                productoController.eliminarProducto(id);
                mostrarMensaje("Producto eliminado correctamente");
            } catch (NumberFormatException e) {
                mostrarMensaje("ID no válido.");
            }
        });
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Operación realizada");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
