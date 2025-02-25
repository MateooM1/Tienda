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
        Button btnBuscarPorPrecio = crearBoton("Buscar por Precio", e -> mostrarBusquedaPorPrecio());

        VBox root = new VBox(15, title, btnAgregar, btnLeer, btnActualizar, btnEliminar,btnBuscarPorPrecio);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f4f4f4;");

        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setTitle("Gestión de Productos");
        primaryStage.show();
    }

    private Button crearBoton(String texto, javafx.event.EventHandler<javafx.event.ActionEvent> evento) {
        Button btn = new Button(texto);
        btn.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-border-radius: 5;");
        btn.setOnAction(evento);
        return btn;
    }

    private void mostrarFormularioProducto(String titulo, String mensaje, String nombreInicial, double precioInicial, Integer id) {
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
            try {
                String nombre = txtNombre.getText().trim();
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                
                if (nombre.isEmpty() || precio <= 0) {
                    mostrarMensaje("Datos inválidos. Verifique los campos.");
                    return;
                }
                
                if (id == null) {
                    productoController.agregarProducto(nombre, precio);
                    mostrarMensaje("Producto agregado correctamente");
                } else {
                    productoController.actualizarProducto(id, nombre, precio);
                    mostrarMensaje("Producto actualizado correctamente");
                }
                stage.close();
            } catch (NumberFormatException ex) {
                mostrarMensaje("Ingrese un precio válido.");
            }
        });

        grid.add(lblNombre, 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(lblPrecio, 0, 1);
        grid.add(txtPrecio, 1, 1);
        grid.add(btnGuardar, 1, 2);

        stage.setScene(new Scene(grid, 350, 200));
        stage.show();
    }

    private void mostrarAgregarProducto() {
        mostrarFormularioProducto("Agregar Producto", "Ingrese los datos del producto", null, 0.0, null);
    }

    private void mostrarActualizarProducto() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Actualizar Producto");
        dialog.setHeaderText("Ingrese el ID del producto a actualizar.");

        dialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                Producto producto = productoController.obtenerProductoPorId(id);
                if (producto != null) {
                    mostrarFormularioProducto("Actualizar Producto", "Modifique los datos del producto", producto.getNombre(), producto.getPrecio(), id);
                } 
            } catch (NumberFormatException e) {
                mostrarMensaje("ID no válido.");
            }
        });
    }

    private void mostrarProductos() {
        List<Producto> productos = productoController.obtenerProductos();
        StringBuilder sb = new StringBuilder();
        productos.forEach(producto -> sb.append(producto).append("\n"));

        TextArea textArea = new TextArea(sb.toString());
        textArea.setEditable(false);

        Stage stage = new Stage();
        VBox vbox = new VBox(new Label("Productos:"), textArea);
        vbox.setPadding(new Insets(20));

        stage.setScene(new Scene(vbox, 400, 300));
        stage.setTitle("Lista de Productos");
        stage.show();
    }

    private void mostrarEliminarProducto() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Eliminar Producto");
        dialog.setHeaderText("Ingrese el ID del producto a eliminar.");

        dialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                productoController.eliminarProducto(id);
                mostrarMensaje("Producto eliminado correctamente");
            } catch (NumberFormatException e) {
                mostrarMensaje("ID no válido.");
            }
        });
    }
    private void mostrarBusquedaPorPrecio() {
        Stage stage = new Stage();
        stage.setTitle("Buscar Productos por Precio");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        Label lblMinPrecio = new Label("Precio Mínimo:");
        TextField txtMinPrecio = new TextField();
        Label lblMaxPrecio = new Label("Precio Máximo:");
        TextField txtMaxPrecio = new TextField();

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-padding: 10px;");
        btnBuscar.setOnAction(e -> {
            try {
                double minPrecio = Double.parseDouble(txtMinPrecio.getText().trim());
                double maxPrecio = Double.parseDouble(txtMaxPrecio.getText().trim());

                List<Producto> productos = productoController.obtenerProductosPorRangoDePrecio(minPrecio, maxPrecio);
                mostrarResultadosBusqueda(productos);

                stage.close();
            } catch (NumberFormatException ex) {
                mostrarMensaje("Ingrese valores numéricos válidos.");
            }
        });

        grid.add(lblMinPrecio, 0, 0);
        grid.add(txtMinPrecio, 1, 0);
        grid.add(lblMaxPrecio, 0, 1);
        grid.add(txtMaxPrecio, 1, 1);
        grid.add(btnBuscar, 1, 2);

        stage.setScene(new Scene(grid, 350, 200));
        stage.show();
    }

    private void mostrarResultadosBusqueda(List<Producto> productos) {
        Stage stage = new Stage();
        stage.setTitle("Resultados de Búsqueda");

        StringBuilder sb = new StringBuilder();
        if (productos.isEmpty()) {
            sb.append("No se encontraron productos en el rango de precios especificado.");
        } else {
            productos.forEach(p -> sb.append(p).append("\n"));
        }

        TextArea textArea = new TextArea(sb.toString());
        textArea.setEditable(false);

        VBox vbox = new VBox(new Label("Productos Encontrados:"), textArea);
        vbox.setPadding(new Insets(20));

        stage.setScene(new Scene(vbox, 400, 300));
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
