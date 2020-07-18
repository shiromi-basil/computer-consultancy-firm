package code;

import db_connection.DBConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * This controller manages the contracts, that is creating, deleting, updating
 * and viewing the contracts, and manages the employee's work.
 *
 * @author Shiromi Basil
 * @since 2019-04-05
 */
public class ControllerManageContract {
    // Making the connection between the database and the program
    DBConnectionClass dbConnectionClass = new DBConnectionClass();
    Connection DBConnection = dbConnectionClass.getConnection();
    @FXML
    private Button btnMainMenu;
    @FXML
    private TextField txtConID;
    @FXML
    private TextField txtConName;
    @FXML
    private DatePicker dateConCreation;
    @FXML
    private TextArea txtConDescription;
    @FXML
    private TextField txtConJobType;
    @FXML
    private TextField txtConLeaderID;
    @FXML
    private TextField txtConCusID;
    @FXML
    private TextField txtConIDDelete;
    @FXML
    private TextField txtConIDView;
    @FXML
    private TextField txtConIDUpdate;
    @FXML
    private TextField txtConNameUp;
    @FXML
    private TextArea txtConDesUp;
    @FXML
    private DatePicker dateConCreationUp;
    @FXML
    private TextField txtConJobTypeUp;
    @FXML
    private TextField txtConLeaderIDUp;
    @FXML
    private TextField txtConCusIDUp;
    @FXML
    private TextField txtEmpIDWork;
    @FXML
    private TextField txtConIDWork;
    @FXML
    private DatePicker dateRegWork;
    @FXML
    private TextField txtConIDViewWork;
    @FXML
    private Label lblConIDDel;
    @FXML
    private Label lblConNameDel;
    @FXML
    private Label lblConDesDel;
    @FXML
    private Label lblConDateDel;
    @FXML
    private Label lblConJobTypeDel;
    @FXML
    private Label lblLeaderIDDel;
    @FXML
    private Label lblConCusIDDel;
    @FXML
    private Label lblConID;
    @FXML
    private Label lblConName;
    @FXML
    private Label lblConDes;
    @FXML
    private Label lblConDate;
    @FXML
    private Label lblConJobType;
    @FXML
    private Label lblLeaderID;
    @FXML
    private Label lblConCusID;
    @FXML
    private Label lblConIDUp;
    @FXML
    private Label lblEmpIDWork;
    @FXML
    private Label lblConIDWork;
    @FXML
    private Label lblDateRegisteredWork;
    // Declaring and assigning variables to hold the information
    // ,regarding Contract, retrieved from the program
    // before passing on to the database
    private int ConID;
    private String ConName;
    private String ConDescription;
    private LocalDate ConCreationDate;
    private String ConJobType;
    private int ConProjectLeaderID;
    private int ConCustomerID;
    private int contractCreateValue = 0;
    private int contractUpdateValue = 0;
    // Declaring and assigning variables to hold the information
    // ,regarding Work, retrieved from the program
    // before passing on to the database
    private int WorkEmpID;
    private int WorkConID;
    private LocalDate WorkDateRegistered;
    private String WorkEmpIDSearch;
    private String WorkDateRegisteredSearch;
    private int workAssignValue = 0;

    /**
     * This method launches onclick of the Main Menu button in the ManageContract.fxml
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
     * This method launches onclick of the Create Contract button of the
     * Create Contract tab in the ManageContract.fxml
     */
    public void createContract(ActionEvent evt) {
        try {
            // Displaying an error message if all the fields have not been filled in
            if ((txtConID.getText()).length() == 0 || (txtConName.getText()).length() == 0 ||
                    (dateConCreation.getValue()) == null || (txtConDescription.getText()).length() == 0 ||
                    (txtConJobType.getText()).length() == 0 || (txtConLeaderID.getText()).length() == 0 ||
                    (txtConCusID.getText()).length() == 0) {
                contractCreateValue = 0;
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
                ConID = Integer.parseInt(txtConID.getText());
                ConName = txtConName.getText();
                ConDescription = txtConDescription.getText();
                ConCreationDate = dateConCreation.getValue();
                ConJobType = txtConJobType.getText();
                ConProjectLeaderID = Integer.parseInt(txtConLeaderID.getText());
                ConCustomerID = Integer.parseInt(txtConCusID.getText());
                // Assigning the variable, the value  1 to show that all data has been verified
                contractCreateValue = 1;
            }
        } catch (NumberFormatException e) {
            contractCreateValue = 0;
            // Displaying error messages if string has been entered into ID and number fields
            try {
                ControllerAlertBox.alertHeader = "Input Mismatch";
                ControllerAlertBox.alertMessage = "Please enter numeric values for IDs.";
                Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                        "/AlertBox.fxml"));
                Stage openStage = new Stage();
                openStage.setTitle("Alert Box");
                openStage.setScene(new Scene(parentNode));
                openStage.showAndWait();
            } catch (IOException exception) {
                System.out.println("This file does not exist");
            }

        }

        // Proceeding further with this method only if the data has been
        // verified and stored in the variables
        if (contractCreateValue == 1) {
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
                } catch (IOException exception) {
                    System.out.println("This file does not exist");
                }
                // Proceeding with program only if user has clicked OK in the confirmation box
                if (ControllerConfirmationBox.confirmValue == 1) {
                    // Executing the sql to insert the data in to the Contract Table
                    String sqlContract = "INSERT INTO computer_consultancy_firm.contract (conID, " +
                            "conName, " +
                            "conDescription, conCreationDate, conJobType, " +
                            "conProjectLeaderID, cusID) VALUES (" +
                            "" + ConID + ", '" + ConName + "', '" + ConDescription + "', " +
                            "'" + ConCreationDate + "', '" + ConJobType + "', " + ConProjectLeaderID + "" +
                            ", " + ConCustomerID + ");";
                    Statement SQLStatement = DBConnection.createStatement();
                    SQLStatement.executeUpdate(sqlContract);
                    try {
                        // Displaying a success message if the change in the database has
                        // been made successfully
                        ControllerAlertBox.alertHeader = "Success!";
                        ControllerAlertBox.alertMessage = "The record has been successfully " +
                                "inserted " +
                                "into the database.";
                        Parent parentNode = FXMLLoader.load(getClass().getResource("." +
                                "design/AlertBox.fxml"));
                        Stage openStage = new Stage();
                        openStage.setTitle("Alert Box");
                        openStage.setScene(new Scene(parentNode));
                        openStage.showAndWait();
                    } catch (IOException exception) {
                        System.out.println("This file does not exist");
                    }
                }
            } catch (SQLException e) {
                // Displaying an error message if an error has occurred while connecting to
                // the database
                try {
                    ControllerAlertBox.alertHeader = "Error";
                    ControllerAlertBox.alertMessage = "An error occurred while inserting the" +
                            " record " +
                            "into the database.";
                    Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                            "/AlertBox.fxml"));
                    Stage openStage = new Stage();
                    openStage.setTitle("Alert Box");
                    openStage.setScene(new Scene(parentNode));
                    openStage.showAndWait();
                } catch (IOException exception) {
                    System.out.println("This file does not exist");
                }
            }
        }
    }

    /**
     * This method launches onclick of the Search Contract button of the
     * Delete Contract tab in the ManageContract.fxml
     */
    public void searchDeleteContract(ActionEvent evt) {
        try {
            // Executing the sql to display the data from the Contract Table
            String sqlContract = "SELECT * FROM computer_consultancy_firm.contract WHERE conID=" +
                    "('" + Integer.parseInt(txtConIDDelete.getText()) + "')";
            Statement SQLStatement = DBConnection.createStatement();
            ResultSet resultSetSQL = SQLStatement.executeQuery(sqlContract);
            while (resultSetSQL.next()) {
                // Displaying the data from the Contract Table in the allocated labels
                lblConIDDel.setText(String.valueOf(resultSetSQL.getInt("conID")));
                lblConNameDel.setText(resultSetSQL.getString("conName"));
                lblConDesDel.setText(resultSetSQL.getString("ConDescription"));
                lblConDateDel.setText(String.valueOf(resultSetSQL.getDate("conCreationDate")));
                lblConJobTypeDel.setText(resultSetSQL.getString("conJobType"));
                lblLeaderIDDel.setText(String.valueOf(resultSetSQL.getInt(
                        "conProjectLeaderID")));
                lblConCusIDDel.setText(String.valueOf(resultSetSQL.getInt("cusID")));
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
            } catch (IOException exception) {
                System.out.println("This file does not exist");
            }
        }
    }

    /**
     * This method launches onclick of the Delete Contract button of the
     * Delete Contract tab in the ManageContract.fxml
     */
    public void deleteContract(ActionEvent evt) {
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
            } catch (IOException exception) {
                System.out.println("This file does not exist");
            }
            // Proceeding with program only if user has clicked OK in the confirmation box
            if (ControllerConfirmationBox.confirmValue == 1) {
                // Executing the sql to delete the data from the Contract Table
                String sqlContract = "DELETE FROM computer_consultancy_firm.contract WHERE conID=" +
                        "('" + lblConIDDel.getText() + "')";
                Statement SQLStatement = DBConnection.createStatement();
                SQLStatement.executeUpdate(sqlContract);
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
                } catch (IOException exception) {
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
            } catch (IOException exception) {
                System.out.println("This file does not exist");
            }
        }
    }

    /**
     * This method launches onclick of the Search Contract button of the
     * Update Contract tab in the ManageContract.fxml
     */
    public void searchUpdateContract(ActionEvent evt) {
        try {
            // Executing the sql to display the data from the Contract Table
            String sqlContract = "SELECT * FROM computer_consultancy_firm.contract WHERE conID=" +
                    "('" + Integer.parseInt(txtConIDUpdate.getText()) + "')";
            Statement SQLStatement = DBConnection.createStatement();
            ResultSet resultSetSQL = SQLStatement.executeQuery(sqlContract);
            while (resultSetSQL.next()) {
                // Displaying the data from the Contract Table in the allocated labels
                lblConIDUp.setText(String.valueOf(resultSetSQL.getInt("conID")));
                txtConNameUp.setText(resultSetSQL.getString("conName"));
                txtConDesUp.setText(resultSetSQL.getString("ConDescription"));
                dateConCreationUp.setValue(LocalDate.parse(String.valueOf(resultSetSQL.getDate("conCreationDate"))));
                txtConJobTypeUp.setText(resultSetSQL.getString("conJobType"));
                txtConLeaderIDUp.setText(String.valueOf(resultSetSQL.getInt(
                        "conProjectLeaderID")));
                txtConCusIDUp.setText(String.valueOf(resultSetSQL.getInt("cusID")));
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
            } catch (IOException exception) {
                System.out.println("This file does not exist");
            }
        }
    }

    /**
     * This method launches onclick of the Update Contract button of the
     * Update Contract tab in the ManageContract.fxml
     */
    public void updateContract(ActionEvent evt) {
        try {
            // Displaying an error message if all the fields hav not been filled in
            if ((txtConCusIDUp.getText()).length() == 0 || (txtConNameUp.getText()).length() == 0 ||
                    (dateConCreationUp.getValue()) == null || (txtConDesUp.getText()).length() == 0 ||
                    (txtConJobTypeUp.getText()).length() == 0 || (txtConLeaderIDUp.getText()).length() == 0) {
                contractUpdateValue = 0;
                try {
                    ControllerAlertBox.alertHeader = "Invalid data!";
                    ControllerAlertBox.alertMessage = "Please fill in all the text fields.";
                    Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                            "/AlertBox.fxml"));
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
                ConID = Integer.parseInt(lblConIDUp.getText());
                ConName = txtConNameUp.getText();
                ConDescription = txtConDesUp.getText();
                ConCreationDate = dateConCreationUp.getValue();
                ConJobType = txtConJobTypeUp.getText();
                ConProjectLeaderID = Integer.parseInt(txtConLeaderIDUp.getText());
                ConCustomerID = Integer.parseInt(txtConCusIDUp.getText());
                // Assigning the variable, the value  1 to show that all data has been verified
                contractUpdateValue = 1;
            }
        } catch (NumberFormatException e) {
            contractUpdateValue = 0;
            try {
                // Displaying error messages if string has been entered into ID and number
                // fields
                ControllerAlertBox.alertHeader = "Input Mismatch";
                ControllerAlertBox.alertMessage = "Please enter numeric values for IDs.";
                Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                        "/AlertBox.fxml"));
                Stage openStage = new Stage();
                openStage.setTitle("Alert Box");
                openStage.setScene(new Scene(parentNode));
                openStage.showAndWait();
            } catch (IOException exception) {
                System.out.println("This file does not exist");
            }

        }

        // Proceeding further with this method only if the data has been
        // verified and stored in the variables
        if (contractUpdateValue == 1) {
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
                } catch (IOException exception) {
                    System.out.println("This file does not exist");
                }
                // Proceeding with program only if user has clicked OK in the confirmation box
                if (ControllerConfirmationBox.confirmValue == 1) {
                    // Executing the sql to update the data in to the Contract Table
                    String sqlContract = "UPDATE computer_consultancy_firm.contract SET" +
                            " conName = '" + ConName + "'," +
                            " conDescription = '" + ConDescription + "'," +
                            " conCreationDate = '" + ConCreationDate + "'," +
                            " conJobType = '" + ConJobType + "'," +
                            " conProjectLeaderID = '" + ConProjectLeaderID + "'," +
                            " cusID = '" + ConCustomerID + "'" +
                            " WHERE conID = " + ConID + ";";
                    Statement SQLStatement = DBConnection.createStatement();
                    System.out.println("44");
                    SQLStatement.executeUpdate(sqlContract);
                    System.out.println("55");
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
                    } catch (IOException exception) {
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
                } catch (IOException exception) {
                    System.out.println("This file does not exist");
                }
            }
        }
    }

    /**
     * This method launches onclick of the View Contract button of the
     * View Contract tab in the ManageContract.fxml
     */
    public void searchViewContract(ActionEvent evt) {
        try {
            // Executing the sql to display the data in to the Contract Table
            String sqlContract = "SELECT * FROM computer_consultancy_firm.contract WHERE conID=" +
                    "('" + Integer.parseInt(txtConIDView.getText()) + "')";
            Statement SQLStatement = DBConnection.createStatement();
            ResultSet resultSetSQL = SQLStatement.executeQuery(sqlContract);
            while (resultSetSQL.next()) {
                // Displaying the data from the Contract Table in the allocated labels
                lblConID.setText(String.valueOf(resultSetSQL.getInt("conID")));
                lblConName.setText(resultSetSQL.getString("conName"));
                lblConDes.setText(resultSetSQL.getString("conDescription"));
                lblConDate.setText(String.valueOf((resultSetSQL.getDate("conCreationDate")).toLocalDate()));
                lblConJobType.setText(resultSetSQL.getString("conJobType"));
                lblLeaderID.setText(String.valueOf(resultSetSQL.getInt("conProjectLeaderID")));
                lblConCusID.setText(String.valueOf(resultSetSQL.getInt("cusID")));
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
            } catch (IOException exception) {
                System.out.println("This file does not exist");
            }
        }
    }

    /**
     * This method launches onclick of the Assign Work button of the
     * Work/Assign Work tab in the ManageContract.fxml
     */
    public void assignWork(ActionEvent evt) {
        try {
            // Displaying an error message if all the fields hav not been filled in
            if ((txtEmpIDWork.getText()).length() == 0 || (txtConIDWork.getText()).length() == 0 ||
                    (dateRegWork.getValue()) == null) {
                workAssignValue = 0;
                try {
                    ControllerAlertBox.alertHeader = "Invalid data!";
                    ControllerAlertBox.alertMessage = "Please fill in all the text fields.";
                    Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                            "/AlertBox.fxml"));
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
                WorkEmpID = Integer.parseInt(txtEmpIDWork.getText());
                WorkConID = Integer.parseInt(txtConIDWork.getText());
                WorkDateRegistered = dateRegWork.getValue();
                // Assigning the variable, the value  1 to show that all data has been verified
                workAssignValue = 1;
            }
        } catch (NumberFormatException e) {
            workAssignValue = 0;
            try {
                // Displaying error messages if string has been entered into ID fields
                ControllerAlertBox.alertHeader = "Input Mismatch";
                ControllerAlertBox.alertMessage = "Please enter numeric values for IDs.";
                Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                        "/AlertBox.fxml"));
                Stage openStage = new Stage();
                openStage.setTitle("Alert Box");
                openStage.setScene(new Scene(parentNode));
                openStage.showAndWait();
            } catch (IOException exception) {
                System.out.println("This file does not exist");
            }

        }

        // Proceeding further with this method only if the data has been
        // verified and stored in the variables
        if (workAssignValue == 1) {
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
                } catch (IOException exception) {
                    System.out.println("This file does not exist");
                }
                // Proceeding with program only if user has clicked OK in the confirmation box
                if (ControllerConfirmationBox.confirmValue == 1) {
                    // Executing the sql to insert the data in to the Employee Work Table
                    String sqlEmployee = "INSERT INTO computer_consultancy_firm.employee_work" +
                            "(eID, conID, " +
                            "dateRegistered) VALUES(" +
                            "" + WorkEmpID + ",'" + WorkConID + "','" + WorkDateRegistered + "')";
                    Statement SQLStatement = DBConnection.createStatement();
                    SQLStatement.executeUpdate(sqlEmployee);
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
                    } catch (IOException exception) {
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
                } catch (IOException exception) {
                    System.out.println("This file does not exist");
                }
            }
        }
    }

    /**
     * This method launches onclick of the View Work button of the
     * Work/View Work tab in the ManageContract.fxml
     */
    public void searchViewWork(ActionEvent evt) {
        try {
            // Setting the variables to null initially, each time this method is called
            WorkEmpIDSearch = "";
            WorkDateRegisteredSearch = "";
            // Executing the sql to display the data in to the Employee Work Table
            String sqlEmployee = "SELECT * FROM computer_consultancy_firm.employee_work WHERE " +
                    "conID=" +
                    "('" + Integer.parseInt(txtConIDViewWork.getText()) + "')";
            Statement SQLStatement = DBConnection.createStatement();
            ResultSet resultSetSQL = SQLStatement.executeQuery(sqlEmployee);
            while (resultSetSQL.next()) {
                // Storing the data from the Employee Work Table in the variables
                WorkConID = resultSetSQL.getInt("conID");
                WorkEmpIDSearch += resultSetSQL.getInt("eID");
                WorkDateRegisteredSearch += resultSetSQL.getDate("dateRegistered");
                WorkEmpIDSearch += "\n";
                WorkDateRegisteredSearch += "\n";
            }
            // Displaying the stored in the variables from the Employee Work Table in the
            // allocated labels
            lblConIDWork.setText(String.valueOf(WorkConID));
            lblEmpIDWork.setText(String.valueOf(WorkEmpIDSearch));
            lblDateRegisteredWork.setText(String.valueOf(WorkDateRegisteredSearch));
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
            } catch (IOException exception) {
                System.out.println("This file does not exist");
            }
        }
    }
}
