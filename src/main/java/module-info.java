module com.ejemplo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.ejemplo to javafx.fxml;  
    exports com.ejemplo;  
    exports com.ejemplo.view to javafx.graphics;  
}
