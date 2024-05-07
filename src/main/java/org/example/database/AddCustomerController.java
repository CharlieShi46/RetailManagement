package org.example.database;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddCustomerController {
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField addressField;
    @FXML private TextField cityField;
    @FXML private TextField zipcodeField;
    @FXML private ChoiceBox<String> genderChoiceBox;
    @FXML private ChoiceBox<String> stateChoiceBox;

    @FXML
    protected void initialize() {
        // Initialize the state choice box with U.S. state abbreviations
        stateChoiceBox.getItems().addAll(
                "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
                "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
                "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
                "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
                "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
        );

        // Initialize the gender choice box with options
        genderChoiceBox.getItems().addAll("Female", "Male", "Others");
    }

    @FXML
    private void handleOkAction() {
        // Get data from fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String address = addressField.getText();
        String city = cityField.getText();
        String state = stateChoiceBox.getValue();  // Ensure this is correctly retrieving the selected value
        String zipcode = zipcodeField.getText();
        String gender = genderChoiceBox.getValue(); // And this as well

        // Insert data into database
        insertCustomer(firstName, lastName, address, city, state, zipcode, gender);

        // Close window
        closeWindow();
    }

    @FXML
    private void handleCancelAction() {
        closeWindow();
    }

    private void insertCustomer(String firstName, String lastName, String address, String city, String state, String zipcode, String gender) {
        String sql = "INSERT INTO Customers (`First Name`, `Last Name`, `Address`, `City`, `State`, `ZipCode`, `Gender`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, address);
            pstmt.setString(4, city);
            pstmt.setString(5, state);
            pstmt.setString(6, zipcode);
            pstmt.setString(7, gender);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            showAlert("Database Error", "Error inserting customer: " + e.getMessage());
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) firstNameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
