package com.ejemplo.view;

import java.util.List;

import com.ejemplo.controller.ClienteController;
import com.ejemplo.controller.PedidoController;
import com.ejemplo.controller.ProductoController;
import com.ejemplo.model.Cliente;
import com.ejemplo.model.Pedido;
import com.ejemplo.model.Producto;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PedidoViewFX extends Application {

    private PedidoController pedidoController;
    private ClienteController clienteController;
    private ProductoController productoController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        pedidoController = new PedidoController();
        clienteController = new ClienteController();
        productoController = new ProductoController();

        Label title = new Label("Gestión de Pedidos");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button btnAgregar = crearBoton("Agregar Pedido", e -> mostrarAgregarPedido());
        Button btnLeer = crearBoton("Ver Pedidos", e -> mostrarPedidos());
        Button btnActualizar = crearBoton("Actualizar Pedido", e -> mostrarActualizarPedido());
        Button btnEliminar = crearBoton("Eliminar Pedido", e -> mostrarEliminarPedido());

        VBox root = new VBox(15, title, btnAgregar, btnLeer, btnActualizar, btnEliminar);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f4f4f4;");

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Gestión de Pedidos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button crearBoton(String texto, javafx.event.EventHandler<javafx.event.ActionEvent> evento) {
        Button btn = new Button(texto);
        btn.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-border-radius: 5;");
        btn.setOnAction(evento);
        return btn;
    }

    private void mostrarAgregarPedido() {
        mostrarFormularioPedido("Agregar Pedido", null, null);
    }

    private void mostrarActualizarPedido() {
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Actualizar Pedido");
        idDialog.setHeaderText("Ingrese el ID del pedido a actualizar.");
        idDialog.setContentText("ID del pedido:");

        idDialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                Pedido pedido = pedidoController.obtenerPedidoPorId(id);
                if (pedido != null) {
                    mostrarFormularioPedido("Actualizar Pedido", pedido.getCliente(), pedido.getProductos(), id);
                } else {
                    mostrarMensaje("Pedido no encontrado.");
                }
            } catch (NumberFormatException e) {
                mostrarMensaje("ID no válido.");
            }
        });
    }

    private void mostrarFormularioPedido(String titulo, Cliente clienteSeleccionado, List<Producto> productosSeleccionados) {
        mostrarFormularioPedido(titulo, clienteSeleccionado, productosSeleccionados, -1);
    }

    private void mostrarFormularioPedido(String titulo, Cliente clienteSeleccionado, List<Producto> productosSeleccionados, int id) {
        Stage stage = new Stage();
        stage.setTitle(titulo);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        Label lblCliente = new Label("Cliente:");
        ComboBox<Cliente> cmbClientes = new ComboBox<>();
        cmbClientes.getItems().addAll(clienteController.obtenerClientes());
        cmbClientes.setValue(clienteSeleccionado);

        Label lblProductos = new Label("Productos:");
        ListView<Producto> lstProductos = new ListView<>();
        lstProductos.getItems().addAll(productoController.obtenerProductos());
        lstProductos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        if (productosSeleccionados != null) {
            for (Producto producto : productosSeleccionados) {
                int index = lstProductos.getItems().indexOf(producto);
                if (index != -1) {
                    lstProductos.getSelectionModel().select(index);
                }
            }
        }
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-padding: 10px;");
        btnGuardar.setOnAction(e -> {
            Cliente cliente = cmbClientes.getValue();
            List<Producto> productos = lstProductos.getSelectionModel().getSelectedItems();

            if (cliente == null || productos.isEmpty()) {
                mostrarMensaje("Debe seleccionar un cliente y al menos un producto.");
                return;
            }

            if (id == -1) {
                pedidoController.agregarPedido(cliente, productos);
                mostrarMensaje("Pedido agregado correctamente");
            } else {
                pedidoController.actualizarPedido(id, cliente, productos);
                mostrarMensaje("Pedido actualizado correctamente");
            }
            stage.close();
        });

        grid.add(lblCliente, 0, 0);
        grid.add(cmbClientes, 1, 0);
        grid.add(lblProductos, 0, 1);
        grid.add(lstProductos, 1, 1);
        grid.add(btnGuardar, 1, 2);

        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarPedidos() {
        List<Pedido> pedidos = pedidoController.obtenerPedidos();
        StringBuilder sb = new StringBuilder();
        for (Pedido pedido : pedidos) {
            sb.append(pedido).append("\n");
        }

        TextArea textArea = new TextArea(sb.toString());
        textArea.setEditable(false);

        Stage stage = new Stage();
        VBox vbox = new VBox(new Label("Pedidos:"), textArea);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 400, 300);
        stage.setTitle("Lista de Pedidos");
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarEliminarPedido() {
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Eliminar Pedido");
        idDialog.setHeaderText("Ingrese el ID del pedido a eliminar.");
        idDialog.setContentText("ID del pedido:");

        idDialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                pedidoController.eliminarPedido(id);
                mostrarMensaje("Pedido eliminado correctamente");
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
