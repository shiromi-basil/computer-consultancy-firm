package code;

import db_connection.DBConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This controller manages the customers, that is creating, deleting, updating
 * and viewing the customers.
 *
 * @author Shiromi Basil
 * @since 2019-04-05
 */
public class ControllerManageCustomer {
    // Making the connection between the database and the program
    DBConnectionClass dbConnectionClass = new DBConnectionClass();
    Connection DBConnection = dbConnectionClass.getConnection();
    @FXML
    private Button btnMainMenu;
    @FXML
    private TextField txtCusIDDelete;
    @FXML
    private TextField txtCusIDSearch;
    @FXML
    private TextField txtCusIDView;
    @FXML
    private TextField txtCusID;
    @FXML
    private TextField txtCusFName;
    @FXML
    private TextField txtCusLName;
    @FXML
    private TextField txtCusNumber;
    @FXML
    private Label lblCusIDUp;
    @FXML
    private TextField txtCusFNameUp;
    @FXML
    private TextField txtCusLNameUp;
    @FXML
    private TextField txtCusNumberUp;
    @FXML
    private Label lblCusID;
    @FXML
    private Label lblCusFName;
    @FXML
    private Label lblCusLName;
    @FXML
    private Label lblCusNumber;
    @FXML
    private Label lblCusIDDel;
    @FXML
    private Label lblCusFNameDel;
    @FXML
    private Label lblCusLNameDel;
    @FXML
    private Label lblCusNumberDel;
    // Declaring and assigning variables to hold the information
    // ,regarding Customer, retrieved from the program
    // before passing on to the database
    private int CusID;
    private String CusFName;
    private String CusLName;
    private int CusNumber;
    private int customerCreateValue = 0;
    private int customerUpdateValue = 0;

    /**
     * This method launches onclick of the Main Menu button in the ManageCustomer.fxml
     */
    public void backMainMenu(ActionEvent evt) {
        try {
            Parent parentNode = FXMLLoader.load(getClass().getResource("../Design/MainMenu" +
                    ".fxml"));
            Stage openStage = new Stage();
            openStage.setTitle("Main Menu");
            openStage.setScene(new Scene(parentNode));
            openStage.show();
        } catch (IOException e) {
            System.out.println("This file does not exist");
        }
        Stage closeStage = (Stage) btnMainMenu.getScene().getWindow();
        closeStage.close();
    }

    /**
     * This method launches onclick of the Create Customer button of the
     * Create Customer tab in the ManageCustomer.fxml
     */
    public void createCustomer(ActionEvent evt) {
        try {
            // Displaying an error message if all the fields have not been filled in
            if ((txtCusFName.getText()).length() == 0 || (txtCusLName.getText()).length() == 0 ||
                    (txtCusNumber.getText()).length() == 0) {
                customerCreateValue =0;
                try {
                    ControllerAlertBox.alertHeader = "Invalid data!";
                    ControllerAlertBox.alertMessage = "Please fill in all the text fields.";
                    Parent parentNode =
                            FXMLLoader.load(getClass().getResource("../design/AlertBox" +
                                    ".fxml"));
                    Stage openStage = new Stage();
                    openStage.setTitle("Alert Box");
                    openStage.setScene(new Scene(parentNode));
                    openStage.showAndWait();
                } catch (IOException e1) {
                    System.out.println("This file does not exist");
                }
            } else if ((txtCusNumber.getText()).length() != 10) {
                customerCreateValue =0;
                // Displaying an error message if the contact number is not 10 digits long
                try {
                    ControllerAlertBox.alertHeader = "Invalid Contact Number!";
                    ControllerAlertBox.alertMessage = "Please enter a 10 digit contact " +
                            "number.";
                    Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                            "/AlertBox.fxml"));
                    Stage openStage = new Stage();
                    openStage.setTitle("Alert Box");
                    openStage.setScene(new Scene(parentNode));
                    openStage.showAndWait();
                } catch (IOException e1) {
                    System.out.println("This file does not exist");
                }
            } else {
                // Saving all the data into variables if all fields have been filled in
                // correctly
                CusID = Integer.parseInt(txtCusID.getText());
                CusFName = txtCusFName.getText();
                CusLName = txtCusLName.getText();
                CusNumber = Integer.parseInt(txtCusNumber.getText());
                // Assigning the variable, the value  1 to show that all data has been verified
                customerCreateValue = 1;
            }
        } catch (NumberFormatException e) {
            customerCreateValue =0;
            try {
                // Displaying error messages if string has been entered into ID and number
                // fields
                ControllerAlertBox.alertHeader = "Input Mismatch";
                ControllerAlertBox.alertMessage = "Please enter numeric values for ID and " +
                        "contact number";
                Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                        "/AlertBox.fxml"));
                Stage openStage = new Stage();
                openStage.setTitle("Alert Box");
                openStage.setScene(new Scene(parentNode));
                openStage.showAndWait();
            } catch (IOException e1) {
                System.out.println("This file does not exist");
            }
        }

        // Proceeding further with this method only if the data has been
        // verified and stored in the variables
        if (customerCreateValue == 1) {
            try {
                try {
                    // Confirming if the user wants to make this change in the database
                    ControllerConfirmationBox.confirmMsg = "Do you want to insert this " +
                            "record into the database?";
                    Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                            "/ConfirmationBox.fxml"));
                    Stage openStage = new Stage();
                    openStage.setTitle("Confirmation Box");
                    openStage.setScene(new Scene(parentNode));
                    openStage.showAndWait();
                } catch (IOException e1) {
                    System.out.println("This file does not exist");
                }
                // Proceeding with program only if user has clicked OK in the confirmation box
                if (ControllerConfirmationBox.confirmValue == 1) {
                    // Executing the sql to insert the data into the Customer Table
                    String sqlCustomer = "INSERT INTO computer_consultancy_firm.customer(cusID, " +
                            "cusFirstName, " +
                            "cusLastName, cusNumber) VALUES(" +
                            "" + CusID + ",'" + CusFName + "','" + CusLName + "','" + CusNumber + "')";
                    Statement SQLStatement = DBConnection.createStatement();
                    SQLStatement.executeUpdate(sqlCustomer);
                    try {
                        // Displaying a success message if the change in the database has
                        // been made successfully
                        ControllerAlertBox.alertHeader = "Success!";
                        ControllerAlertBox.alertMessage = "The record has been successfully " +
                                "inserted into the database.";
                        Parent parentNode = FXMLLoader.load(getClass().getResource("." +
                                "design/AlertBox.fxml"));
                        Stage openStage = new Stage();
                        openStage.setTitle("Alert Box");
                        openStage.setScene(new Scene(parentNode));
                        openStage.showAndWait();
                    } catch (IOException e1) {
                        System.out.println("This file does not exist");
                    }
                }
            } catch (SQLException e) {
                try {
                    // Displaying an error message if the an error has occurred while
                    // connecting to the database
                    ControllerAlertBox.alertHeader = "Error";
                    ControllerAlertBox.alertMessage = "An error occurred while inserting the" +
                            " record into the database.";
                    Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                            "/AlertBox.fxml"));
                    Stage openStage = new Stage();
                    openStage.setTitle("Alert Box");
                    openStage.setScene(new Scene(parentNode));
                    openStage.showAndWait();
                } catch (IOException e1) {
                    System.out.println("This file does not exist");
                }
            }
        }
    }

    /**
     * This method launches onclick of the Search Customer button of the
     * Delete Customer tab in the ManageCustomer.fxml
     */
    public void searchDeleteCustomer(ActionEvent evt) {
        try {
            // Executing the sql to display the data from the Customer Table
            String sqlCustomer = "SELECT * FROM computer_consultancy_firm.customer WHERE cusID=" +
                    "('" + Integer.parseInt(txtCusIDDelete.getText()) + "')";
            Statement SQLStatement = DBConnection.createStatement();
            ResultSet resultSetSQL = SQLStatement.executeQuery(sqlCustomer);
            while (resultSetSQL.next()) {
                lblCusIDDel.setText(String.valueOf(resultSetSQL.getInt("cusID")));
                lblCusFNameDel.setText(resultSetSQL.getString("cusFirstName"));
                lblCusLNameDel.setText(resultSetSQL.getString("cusLastName"));
                lblCusNumberDel.setText(String.valueOf(resultSetSQL.getInt("cusNumber")));
            }
        } catch (SQLException e) {
            try {
                // Displaying an error message if the an error has occurred while connecting
                // to the database
                ControllerAlertBox.alertHeader = "Error";
                ControllerAlertBox.alertMessage = "An error occurred while retrieving the " +
                        "record from the database.";
                Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                        "/AlertBox.fxml"));
                Stage openStage = new Stage();
                openStage.setTitle("Alert Box");
                openStage.setScene(new Scene(parentNode));
                openStage.showAndWait();
            } catch (IOException e1) {
                System.out.println("This file does not exist");
            }
        }
    }

    /**
     * This method launches onclick of the Delete Customer button of the
     * Delete Customer tab in the ManageCustomer.fxml
     */
    public void deleteCustomer(ActionEvent evt) {
        try {
            try {
                // Confirming if the user wants to make this change in the database
                ControllerConfirmationBox.confirmMsg = "Do you want to delete this record " +
                        "from the database?";
                Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                        "/ConfirmationBox.fxml"));
                Stage openStage = new Stage();
                openStage.setTitle("Confirmation Box");
                openStage.setScene(new Scene(parentNode));
                openStage.showAndWait();
            } catch (IOException e1) {
                System.out.println("This file does not exist");
            }
            // Proceeding with program only if user has clicked OK in the confirmation box
            if (ControllerConfirmationBox.confirmValue == 1) {
                // Executing the sql to delete the data from the Customer Table
                String sqlCustomer = "DELETE FROM computer_consultancy_firm.customer WHERE cusID=" +
                        "('" + lblCusIDDel.getText() + "')";
                Statement SQLStatement = DBConnection.createStatement();
                SQLStatement.executeUpdate(sqlCustomer);
                try {
                    // Displaying a success message if the change in the database has been
                    // made successfully
                    ControllerAlertBox.alertHeader = "Success!";
                    ControllerAlertBox.alertMessage = "The record has been successfully " +
                            "deleted from the database.";
                    Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                            "/AlertBox.fxml"));
                    Stage openStage = new Stage();
                    openStage.setTitle("Alert Box");
                    openStage.setScene(new Scene(parentNode));
                    openStage.showAndWait();
                } catch (IOException e1) {
                    System.out.println("This file does not exist");
                }
            }
        } catch (SQLException e) {
            try {
                // Displaying an error message if the an error has occurred while connecting
                // to the database
                ControllerAlertBox.alertHeader = "Error";
                ControllerAlertBox.alertMessage = "An error occurred while deleting the " +
                        "record from the database.";
                Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                        "/AlertBox.fxml"));
                Stage openStage = new Stage();
                openStage.setTitle("Alert Box");
                openStage.setScene(new Scene(parentNode));
                openStage.showAndWait();
            } catch (IOException e1) {
                System.out.println("This file does not exist");
            }
        }
    }

    /**
     * This method launches onclick of the Search Customer button of the
     * Update Customer tab in the ManageCustomer.fxml
     */
    public void searchUpdateCustomer(ActionEvent evt) {
        try {
            // Executing the sql to display the data from the Customer Table
            String sqlCustomer = "SELECT * FROM computer_consultancy_firm.customer WHERE cusID=" +
                    "('" + Integer.parseInt(txtCusIDSearch.getText()) + "')";
            Statement SQLStatement = DBConnection.createStatement();
            ResultSet resultSetSQL = SQLStatement.executeQuery(sqlCustomer);
            while (resultSetSQL.next()) {
                lblCusIDUp.setText(String.valueOf(resultSetSQL.getInt("cusID")));
                txtCusFNameUp.setText(resultSetSQL.getString("cusFirstName"));
                txtCusLNameUp.setText(resultSetSQL.getString("cusLastName"));
                txtCusNumberUp.setText(String.valueOf(resultSetSQL.getInt("cusNumber")));
            }
        } catch (SQLException e) {
            try {
                // Displaying an error message if the an error has occurred while connecting
                // to the database
                ControllerAlertBox.alertHeader = "Error";
                ControllerAlertBox.alertMessage = "An error occurred while retrieving the " +
                        "record from the database.";
                Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                        "/AlertBox.fxml"));
                Stage openStage = new Stage();
                openStage.setTitle("Alert Box");
                openStage.setScene(new Scene(parentNode));
                openStage.showAndWait();
            } catch (IOException e1) {
                System.out.println("This file does not exist");
            }
        }
    }

    /**
     * This method launches onclick of the Update Customer button of the
     * Update Customer tab in the ManageCustomer.fxml
     */
    public void updateCustomer(ActionEvent evt) {
        try {
            // Displaying an error message if all the fields have not been filled in
            if ((txtCusFNameUp.getText()).length() == 0 || (txtCusLNameUp.getText()).length() == 0 ||
                    (txtCusNumberUp.getText()).length() == 0) {
                customerUpdateValue =0;
                try {
                    ControllerAlertBox.alertHeader = "Invalid data!";
                    ControllerAlertBox.alertMessage = "Please fill in all the text fields.";
                    Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                            "/AlertBox.fxml"));
                    Stage openStage = new Stage();
                    openStage.setTitle("Alert Box");
                    openStage.setScene(new Scene(parentNode));
                    openStage.showAndWait();
                } catch (IOException e1) {
                    System.out.println("This file does not exist");
                }
            } else if ((txtCusNumberUp.getText()).length() != 10) {
                customerUpdateValue =0;
                try {
                    ControllerAlertBox.alertHeader = "Invalid Contact Number!";
                    ControllerAlertBox.alertMessage = "Please enter a 10 digit contact " +
                            "number.";
                    Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                            "/AlertBox.fxml"));
                    Stage openStage = new Stage();
                    openStage.setTitle("Alert Box");
                    openStage.setScene(new Scene(parentNode));
                    openStage.showAndWait();
                } catch (IOException e1) {
                    System.out.println("This file does not exist");
                }
            } else {
                // Saving all the data into variables if all fields have been filled in
                // correctly
                CusID = Integer.parseInt(lblCusIDUp.getText());
                CusFName = txtCusFNameUp.getText();
                CusLName = txtCusLNameUp.getText();
                CusNumber = Integer.parseInt(txtCusNumberUp.getText());
                // Assigning the variable, the value  1 to show that all data has been verified
                customerUpdateValue = 1;
            }
        } catch (NumberFormatException e) {
            customerUpdateValue =0;
            try {
                // Displaying error messages if string has been entered into ID and number
                // fields
                ControllerAlertBox.alertHeader = "Input Mismatch";
                ControllerAlertBox.alertMessage = "Please enter numeric values for ID and " +
                        "contact number";
                Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                        "/AlertBox.fxml"));
                Stage openStage = new Stage();
                openStage.setTitle("Alert Box");
                openStage.setScene(new Scene(parentNode));
                openStage.showAndWait();
            } catch (IOException e1) {
                System.out.println("This file does not exist");
            }

        }

        // Proceeding further with this method only if the data has been
        // verified and stored in the variables
        if (customerUpdateValue == 1) {
            try {
                try {
                    // Confirming if the user wants to make this change in the database
                    ControllerConfirmationBox.confirmMsg = "Do you want to update this " +
                            "record in the database?";
                    Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                            "/ConfirmationBox.fxml"));
                    Stage openStage = new Stage();
                    openStage.setTitle("Confirmation Box");
                    openStage.setScene(new Scene(parentNode));
                    openStage.showAndWait();
                } catch (IOException e1) {
                    System.out.println("This file does not exist");
                }
                // Proceeding with program only if user has clicked OK in the confirmation box
                if (ControllerConfirmationBox.confirmValue == 1) {
                    // Executing the sql to update the data from the Customer Table
                    String sqlCustomer = "UPDATE computer_consultancy_firm.customer SET" +
                            " cusFirstName = '" + CusFName + "'," +
                            " cusLastName = '" + CusLName + "'," +
                            " cusNumber = '" + CusNumber + "'" +
                            " WHERE cusID = " + CusID + ";";
                    Statement SQLStatement = DBConnection.createStatement();
                    SQLStatement.executeUpdate(sqlCustomer);
                    try {
                        // Displaying a success message if the change in the database has
                        // been made successfully
                        ControllerAlertBox.alertHeader = "Success!";
                        ControllerAlertBox.alertMessage = "The record has been successfully " +
                                "updated in the database.";
                        Parent parentNode = FXMLLoader.load(getClass().getResource("." +
                                "design/AlertBox.fxml"));
                        Stage openStage = new Stage();
                        openStage.setTitle("Alert Box");
                        openStage.setScene(new Scene(parentNode));
                        openStage.showAndWait();
                    } catch (IOException e1) {
                        System.out.println("This file does not exist");
                    }
                }
            } catch (SQLException e) {
                try {
                    // Displaying an error message if the an error has occurred while
                    // connecting to the database
                    ControllerAlertBox.alertHeader = "Error";
                    ControllerAlertBox.alertMessage = "An error occurred while updating the " +
                            "record in the database.";
                    Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                            "/AlertBox.fxml"));
                    Stage openStage = new Stage();
                    openStage.setTitle("Alert Box");
                    openStage.setScene(new Scene(parentNode));
                    openStage.showAndWait();
                } catch (IOException e1) {
                    System.out.println("This file does not exist");
                }
            }
        }
    }

    /**
     * This method launches onclick of the View Customer button of the
     * View Customer tab in the ManageCustomer.fxml
     */
    public void searchViewCustomer(ActionEvent evt) {
        try {
            // Executing the sql to display the data from the Customer Table
            String sqlCustomer = "SELECT * FROM computer_consultancy_firm.customer WHERE cusID=" +
                    "('" + Integer.parseInt(txtCusIDView.getText()) + "')";
            Statement SQLStatement = DBConnection.createStatement();
            ResultSet resultSetSQL = SQLStatement.executeQuery(sqlCustomer);
            while (resultSetSQL.next()) {
                lblCusID.setText(String.valueOf(resultSetSQL.getInt("cusID")));
                lblCusFName.setText(resultSetSQL.getString("cusFirstName"));
                lblCusLName.setText(resultSetSQL.getString("cusLastName"));
                lblCusNumber.setText(String.valueOf(resultSetSQL.getInt("cusNumber")));
            }
        } catch (SQLException e) {
            try {
                // Displaying an error message if the an error has occurred while connecting
                // to the database
                ControllerAlertBox.alertHeader = "Error";
                ControllerAlertBox.alertMessage = "An error occurred while retrieving the " +
                        "record from the database.";
                Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                        "/AlertBox.fxml"));
                Stage openStage = new Stage();
                openStage.setTitle("Alert Box");
                openStage.setScene(new Scene(parentNode));
                openStage.showAndWait();
            } catch (IOException e1) {
                System.out.println("This file does not exist");
            }
        }
    }
}
