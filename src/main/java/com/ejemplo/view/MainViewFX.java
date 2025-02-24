package com.ejemplo.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainViewFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menú Principal");

        Button btnClientes = new Button("Gestión de Clientes");
        btnClientes.setOnAction(e -> new ClienteViewFX().start(new Stage()));

        Button btnProductos = new Button("Gestión de Productos");
        btnProductos.setOnAction(e -> new ProductoViewFX().start(new Stage()));

        Button btnPedidos = new Button("Gestión de Pedidos");
        btnPedidos.setOnAction(e -> new PedidoViewFX().start(new Stage()));

        VBox root = new VBox(15, btnClientes, btnProductos, btnPedidos);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
