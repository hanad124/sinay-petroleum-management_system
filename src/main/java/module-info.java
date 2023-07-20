module com.example.petroleumsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;


    opens com.example.petroleumsystem to javafx.fxml;
    exports com.example.petroleumsystem;
}