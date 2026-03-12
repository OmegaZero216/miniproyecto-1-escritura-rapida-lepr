module com.example.escritura_rapida {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.net.http;


    opens com.example.escritura_rapida to javafx.fxml;
    opens com.example.escritura_rapida.controller to javafx.fxml;
    exports com.example.escritura_rapida;
}