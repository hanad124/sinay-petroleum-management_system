module com.example.petroleumsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.petroleumsystem to javafx.fxml;
    exports com.example.petroleumsystem;
}