package org.example.database;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {
    @FXML private TableView<Person> peopleTableView;
    @FXML private TableView<Merchandise> merchandiseTableView;

    @FXML private TableColumn<Person, String> firstNameColumn, lastNameColumn, addressColumn, cityColumn, stateColumn, zipcodeColumn, genderColumn;
    @FXML private TableColumn<Merchandise, String> nameColumn, descriptionColumn;

    @FXML private MenuItem listAllEmployees, listAllCustomers, listAllMerchandise;
    @FXML
    private void refreshData() {
        loadPeople("SELECT * FROM Employees"); // or any other initial load you want
        loadMerchandise();
    }

    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

    @FXML
    public void initialize() {
        // Set up person columns
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        zipcodeColumn.setCellValueFactory(new PropertyValueFactory<>("zipcode"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        // Set up merchandise columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Load data actions
        listAllEmployees.setOnAction(e -> loadPeople("SELECT * FROM Employees"));
        listAllCustomers.setOnAction(e -> loadPeople("SELECT * FROM Customers"));
        listAllMerchandise.setOnAction(e -> loadMerchandise());
    }

    @FXML
    private void handleAddOneCustomer() {
        try {
            // Load the FXML file for adding a customer
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customer_add.fxml"));
            Parent root = loader.load();

            // Create a new stage (window) for the add customer form
            Stage stage = new Stage();
            stage.setTitle("Add New Customer");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Set modality if needed
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions (e.g., FXML file not found)
        }
    }

    @FXML
    private void handleAddOneEmployee() {
        try {
            // Load the FXML file for adding a customer
            FXMLLoader loader = new FXMLLoader(getClass().getResource("employee_add.fxml"));
            Parent root = loader.load();

            // Create a new stage (window) for the add customer form
            Stage stage = new Stage();
            stage.setTitle("Add New Employee");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Set modality if needed
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions (e.g., FXML file not found)
        }
    }
    @FXML
    private void handleAddOneMerchandise() {
        try {
            // Load the FXML file for adding a customer
            FXMLLoader loader = new FXMLLoader(getClass().getResource("merchandise_add.fxml"));
            Parent root = loader.load();

            // Create a new stage (window) for the add customer form
            Stage stage = new Stage();
            stage.setTitle("Add New Merchandise");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Set modality if needed
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions (e.g., FXML file not found)
        }
    }

    private void loadPeople(String sql) {
        ObservableList<Person> data = FXCollections.observableArrayList();
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                data.add(new Person(
                        rs.getString("First Name"),
                        rs.getString("Last Name"),
                        rs.getString("Address"),
                        rs.getString("City"),
                        rs.getString("State"),
                        rs.getString("Zipcode"),
                        rs.getString("Gender")
                ));
            }
            peopleTableView.setItems(data);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load data", e);
            showAlert("Error Loading Data", STR."Cannot load data from database: \{e.getMessage()}");
        }
    }

    private void loadMerchandise() {
        ObservableList<Merchandise> data = FXCollections.observableArrayList();
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT Name, Description FROM Merchandise")) {
            while (rs.next()) {
                data.add(new Merchandise(
                        rs.getString("Name"),
                        rs.getString("Description")
                ));
            }
            merchandiseTableView.setItems(data);
            System.out.println("Data loaded: " + data.size() + " items.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load merchandise", e);
            showAlert("Error Loading Merchandise", "Cannot load merchandise from database: " + e.getMessage());
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public class Person {
        private final SimpleStringProperty firstName, lastName, address, city, state, zipcode, gender;

        public Person(String firstName, String lastName, String address, String city, String state, String zipcode, String gender) {
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
            this.address = new SimpleStringProperty(address);
            this.city = new SimpleStringProperty(city);
            this.state = new SimpleStringProperty(state);
            this.zipcode = new SimpleStringProperty(zipcode);
            this.gender = new SimpleStringProperty(gender);
        }

        // Property getters for JavaFX binding
        public SimpleStringProperty firstNameProperty() { return firstName; }
        public SimpleStringProperty lastNameProperty() { return lastName; }
        public SimpleStringProperty addressProperty() { return address; }
        public SimpleStringProperty cityProperty() { return city; }
        public SimpleStringProperty stateProperty() { return state; }
        public SimpleStringProperty zipcodeProperty() { return zipcode; }
        public SimpleStringProperty genderProperty() { return gender; }

        // Regular getters
        public String getFirstName() { return firstName.get(); }
        public String getLastName() { return lastName.get(); }
        public String getAddress() { return address.get(); }
        public String getCity() { return city.get(); }
        public String getState() { return state.get(); }
        public String getZipcode() { return zipcode.get(); }
        public String getGender() { return gender.get(); }
    }


    public class Merchandise {
        private final SimpleStringProperty name, description;

        public Merchandise(String name, String description) {
            this.name = new SimpleStringProperty(name);
            this.description = new SimpleStringProperty(description);
        }

        // Property getters for JavaFX binding
        public SimpleStringProperty nameProperty() { return name; }
        public SimpleStringProperty descriptionProperty() { return description; }

        // Regular getters
        public String getName() { return name.get(); }
        public String getDescription() { return description.get(); }
    }

}

