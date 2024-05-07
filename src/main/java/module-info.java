module org.example.database {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.database to javafx.fxml;
    exports org.example.database;
}