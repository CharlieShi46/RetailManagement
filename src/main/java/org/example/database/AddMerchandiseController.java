package org.example.database;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AddMerchandiseController {
    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextField descriptionField;

    @FXML
    protected void initialize() {
        // Initialization logic can go here (if any is needed)
    }

    @FXML
    private void handleOkAction() {
        // Get data from the form fields
        String name = nameField.getText();
        String price = priceField.getText();
        String description = descriptionField.getText();

        // Insert the new merchandise data into the database
        insertMerchandise(name, price, description);

        // Optionally close the window
        closeWindow();
    }

    @FXML
    private void handleCancelAction() {
        // Close the window without saving
        closeWindow();
    }

    private void insertMerchandise(String name, String price, String description) {
        // Implement the database insertion logic here
        // This is a stub and should be replaced with actual database interaction code
        try {
            // Assume DatabaseConnector provides a static method to get a connection
            try (Connection conn = DatabaseConnector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(
                         "INSERT INTO Merchandise (Name, Price, Description) VALUES (?, ?, ?)")) {
                pstmt.setString(1, name);
                pstmt.setString(2, price);
                pstmt.setString(3, description);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Error inserting merchandise: " + e.getMessage());
        }
    }

    private void closeWindow() {
        // Get a reference to the stage
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        // Show an error alert
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
