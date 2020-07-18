package code;

import db_connection.DBConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This controller manages the role of the employee, that is creating, deleting,
 * updating and  viewing the roles.
 *
 * @author Shiromi Basil
 * @since 2019-04-05
 */
public class ControllerManageRole {
    // Making the connection between the database and the program
    DBConnectionClass dbConnectionClass = new DBConnectionClass();
    Connection DBConnection = dbConnectionClass.getConnection();
    @FXML
    private Button btnMainMenu;
    @FXML
    private TextField txtRoleID;
    @FXML
    private TextField txtRoleName;
    @FXML
    private TextArea txtRoleDescription;
    @FXML
    private TextField txtRoleHourlyPay;
    @FXML
    private TextField txtRoleIDDelete;
    @FXML
    private TextField txtRoleIDSearch;
    @FXML
    private TextField txtRoleNameUp;
    @FXML
    private TextArea txtRoleDescriptionUp;
    @FXML
    private TextField txtRoleHourlyPayUp;
    @FXML
    private TextField txtRoleIDView;
    @FXML
    private Label lblRoleIDDel;
    @FXML
    private Label lblRoleNameDel;
    @FXML
    private Label lblRoleDescriptionDel;
    @FXML
    private Label lblRoleHourlyPayDel;
    @FXML
    private Label lblRoleIDUp;
    @FXML
    private Label lblRoleID;
    @FXML
    private Label lblRoleName;
    @FXML
    private Label lblRoleDescription;
    @FXML
    private Label lblRoleHourlyPay;
    // Declaring and assigning variables to hold the information
    // ,regarding Role, retrieved from the program
    // before passing on to the database
    private int RoleID;
    private String RoleName;
    private String RoleDescription;
    private double RoleHourlyPay;
    private int roleCreateValue = 0;
    private int roleUpdateValue = 0;

    /**
     * This method launches onclick of the Main Menu button in the ManageRole.fxml
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
     * This method launches onclick of the Create Role button of the
     * Create Role tab in the ManageRole.fxml
     */
    public void createRole(ActionEvent evt) {
        try {
            // Displaying an error message if all the fields have not been filled in
            if ((txtRoleName.getText()).length() == 0 || (txtRoleDescription.getText()).length() == 0 ||
                    (txtRoleID.getText()).length() == 0 || (txtRoleHourlyPay.getText()).length() == 0) {
                roleCreateValue = 0;
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
                } catch (IOException exception) {
                    System.out.println("This file does not exist");
                }
            } else {
                // Saving all the data into variables if all fields have been filled in
                // correctly
                RoleID = Integer.parseInt(txtRoleID.getText());
                RoleName = txtRoleName.getText();
                RoleDescription = txtRoleDescription.getText();
                RoleHourlyPay = Double.parseDouble(txtRoleHourlyPay.getText());
                // Assigning the variable, the value  1 to show that all data has been verified
                roleCreateValue = 1;
            }
        } catch (NumberFormatException e) {
            roleCreateValue = 0;
            try {
                // Displaying error messages if string has been entered into ID and hourly
                // pay fields
                ControllerAlertBox.alertHeader = "Input Mismatch";
                ControllerAlertBox.alertMessage = "Please enter numeric values for ID and " +
                        "hourly pay";
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
        if (roleCreateValue == 1) {
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
                    // Executing the sql to insert the data in to the Role Table
                    String sqlRole = "INSERT INTO computer_consultancy_firm.role(rID, rName, " +
                            "rDescription, rHourlyPay) VALUES(" +
                            "'" + RoleID + "','" + RoleName + "'," +
                            "'" + RoleDescription + "','" + RoleHourlyPay + "')";
                    Statement SQLStatement = DBConnection.createStatement();
                    SQLStatement.executeUpdate(sqlRole);
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
                // Displaying an error message if an error has occurred while connecting to
                // the database
                try {
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
     * This method launches onclick of the Search Role button of the
     * Delete Role tab in the ManageRole.fxml
     */
    public void searchDeleteRole(ActionEvent evt) {
        try {
            // Executing the sql to display the data from the Role Table
            String sqlRole = "SELECT * FROM computer_consultancy_firm.role WHERE rID=" +
                    "('" + Integer.parseInt(txtRoleIDDelete.getText()) + "')";
            Statement SQLStatement = DBConnection.createStatement();
            ResultSet resultSetSQL = SQLStatement.executeQuery(sqlRole);
            while (resultSetSQL.next()) {
                // Displaying the data from the Role Table in the allocated labels
                lblRoleIDDel.setText(String.valueOf(resultSetSQL.getInt("rID")));
                lblRoleNameDel.setText(resultSetSQL.getString("rName"));
                lblRoleDescriptionDel.setText(resultSetSQL.getString("rDescription"));
                lblRoleHourlyPayDel.setText(String.valueOf(resultSetSQL.getDouble(
                        "rHourlyPay")));
            }
        } catch (SQLException e) {
            // Displaying an error message if the an error has occurred while connecting to
            // the database
            try {
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
     * This method launches onclick of the Delete Role button of the
     * Delete Role tab in the ManageRole.fxml
     */
    public void deleteRole(ActionEvent evt) {
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
                // Executing the sql to delete the data from the Role Table
                String sqlRole = "DELETE FROM computer_consultancy_firm.role WHERE rID=" +
                        "('" + lblRoleIDDel.getText() + "')";
                Statement SQLStatement = DBConnection.createStatement();
                SQLStatement.executeUpdate(sqlRole);
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
     * This method launches onclick of the Search Role button of the
     * Update Role tab in the ManageRole.fxml
     */
    public void searchUpdateRole(ActionEvent evt) {
        try {
            // Executing the sql to display the data from the Role Table
            String sqlRole = "SELECT * FROM computer_consultancy_firm.role WHERE rID=" +
                    "('" + Integer.parseInt(txtRoleIDSearch.getText()) + "')";
            Statement SQLStatement = DBConnection.createStatement();
            ResultSet resultSetSQL = SQLStatement.executeQuery(sqlRole);
            while (resultSetSQL.next()) {
                // Displaying the data from the Role Table in the allocated labels
                lblRoleIDUp.setText(String.valueOf(resultSetSQL.getInt("rID")));
                txtRoleNameUp.setText(resultSetSQL.getString("rName"));
                txtRoleDescriptionUp.setText(resultSetSQL.getString("rDescription"));
                txtRoleHourlyPayUp.setText(String.valueOf(resultSetSQL.getDouble("rHourlyPay"
                )));
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
     * This method launches onclick of the Update Role button of the
     * Update Role tab in the ManageRole.fxml
     */
    public void updateRole(ActionEvent evt) {
        try {
            // Displaying an error message if all the fields hav not been filled in
            if ((txtRoleNameUp.getText()).length() == 0 || (txtRoleDescriptionUp.getText()).length() == 0 ||
                    (txtRoleHourlyPayUp.getText()).length() == 0) {
                roleUpdateValue = 0;
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
            } else {
                // Saving all the data into variables if all fields have been filled in
                // correctly
                RoleID = Integer.parseInt(lblRoleIDUp.getText());
                RoleName = txtRoleNameUp.getText();
                RoleDescription = txtRoleDescriptionUp.getText();
                RoleHourlyPay = Double.parseDouble(txtRoleHourlyPayUp.getText());
                // Assigning the variable, the value  1 to show that all data has been verified
                roleUpdateValue = 1;
            }
        } catch (NumberFormatException e) {
            roleUpdateValue = 0;
            try {
                // Displaying error messages if string has been entered into ID and hourly
                // pay fields
                ControllerAlertBox.alertHeader = "Input Mismatch";
                ControllerAlertBox.alertMessage = "Please enter numeric values for ID and " +
                        "hourly pay";
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
        if (roleUpdateValue == 1) {
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
                    // Executing the sql to update the data in to the Role Table
                    String sqlRole = "UPDATE computer_consultancy_firm.role SET" +
                            " rName = '" + RoleName + "'," +
                            " rDescription = '" + RoleDescription + "'," +
                            " rHourlyPay = '" + RoleHourlyPay + "'" +
                            " WHERE rID = " + RoleID + ";";
                    Statement SQLStatement = DBConnection.createStatement();
                    SQLStatement.executeUpdate(sqlRole);
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
     * This method launches onclick of the Search Role button of the
     * View Role tab in the ManageRole.fxml
     */
    public void searchViewRole(ActionEvent evt) {
        try {
            // Executing the sql to display the data in to the Role Table
            String sqlRole = "SELECT * FROM computer_consultancy_firm.role WHERE rID=" +
                    "('" + Integer.parseInt(txtRoleIDView.getText()) + "')";
            Statement SQLStatement = DBConnection.createStatement();
            ResultSet resultSetSQL = SQLStatement.executeQuery(sqlRole);
            while (resultSetSQL.next()) {
                // Displaying the data from the Contract Table in the allocated labels
                lblRoleID.setText(String.valueOf(resultSetSQL.getInt("rID")));
                lblRoleName.setText(resultSetSQL.getString("rName"));
                lblRoleDescription.setText(resultSetSQL.getString("rDescription"));
                lblRoleHourlyPay.setText(String.valueOf(resultSetSQL.getDouble("rHourlyPay")));
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
