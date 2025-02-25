package com.ejemplo.view;

import java.util.List;

import com.ejemplo.controller.ClienteController;
import com.ejemplo.controller.PedidoController;
import com.ejemplo.controller.ProductoController;
import com.ejemplo.model.Cliente;
import com.ejemplo.model.Pedido;
import com.ejemplo.model.Producto;

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

public class PedidoViewFX {

    private final PedidoController pedidoController = new PedidoController();
    private final ClienteController clienteController = new ClienteController();
    private final ProductoController productoController = new ProductoController();

    public void start(Stage primaryStage) {
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
        mostrarFormularioPedido("Agregar Pedido", null, null, -1);
    }

    private void mostrarActualizarPedido() {
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Actualizar Pedido");
        idDialog.setHeaderText("Ingrese el ID del pedido a actualizar.");
        idDialog.setContentText("ID del pedido:");

        idDialog.showAndWait().ifPresent(idStr -> {
            Pedido pedido = pedidoController.obtenerPedidoPorId(Integer.parseInt(idStr));
            if (pedido != null) {
                mostrarFormularioPedido("Actualizar Pedido", pedido.getCliente(), pedido.getProductos(), pedido.getId());
            } else {
                mostrarMensaje("Pedido no encontrado.", Alert.AlertType.WARNING);
            }
        });
    }

    private void mostrarEliminarPedido() {
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Eliminar Pedido");
        idDialog.setHeaderText("Ingrese el ID del pedido a eliminar.");
        idDialog.setContentText("ID del pedido:");

        idDialog.showAndWait().ifPresent(idStr -> {
            pedidoController.eliminarPedido(Integer.parseInt(idStr));
            mostrarMensaje("Pedido eliminado correctamente", Alert.AlertType.INFORMATION);
        });
    }

    private void mostrarPedidos() {
        List<Pedido> pedidos = pedidoController.obtenerPedidos();
        TextArea textArea = new TextArea(pedidos.toString());
        textArea.setEditable(false);

        Stage stage = new Stage();
        VBox vbox = new VBox(new Label("Pedidos:"), textArea);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 400, 300);
        stage.setTitle("Lista de Pedidos");
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarFormularioPedido(String titulo, Cliente clienteSeleccionado, List<Producto> productosSeleccionados, int id) {
        Stage stage = new Stage();
        stage.setTitle(titulo);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        ComboBox<Cliente> cmbClientes = new ComboBox<>();
        cmbClientes.getItems().addAll(clienteController.obtenerClientes());
        cmbClientes.setValue(clienteSeleccionado);

        ListView<Producto> lstProductos = new ListView<>();
        lstProductos.getItems().addAll(productoController.obtenerProductos());
        lstProductos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(e -> {
            Cliente cliente = cmbClientes.getValue();
            List<Producto> productos = lstProductos.getSelectionModel().getSelectedItems();
            if (cliente == null || productos.isEmpty()) {
                mostrarMensaje("Debe seleccionar un cliente y al menos un producto.", Alert.AlertType.ERROR);
                return;
            }
            if (id == -1) {
                pedidoController.agregarPedido(cliente, productos);
                mostrarMensaje("Pedido agregado correctamente", Alert.AlertType.INFORMATION);
            } else {
                pedidoController.actualizarPedido(id, cliente, productos);
                mostrarMensaje("Pedido actualizado correctamente", Alert.AlertType.INFORMATION);
            }
            stage.close();
        });

        grid.add(new Label("Cliente:"), 0, 0);
        grid.add(cmbClientes, 1, 0);
        grid.add(new Label("Productos:"), 0, 1);
        grid.add(lstProductos, 1, 1);
        grid.add(btnGuardar, 1, 2);

        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarMensaje(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
