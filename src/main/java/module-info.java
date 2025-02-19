module com.ejemplo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.ejemplo to javafx.fxml;  // Asegura que las clases de FXML se puedan abrir para JavaFX
    exports com.ejemplo;  // Exporta el paquete raíz
    exports com.ejemplo.view to javafx.graphics;  // Exporta el paquete view a javafx.graphics
}
