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
 * This controller manages the employees, that is creating, deleting, updating
 * and viewing employees, manages work and calculation of the pay.
 *
 * @author Shiromi Basil
 * @since 2019-04-05
 */
public class ControllerManageEmployee {
    // Making the connection between the database and the program
    DBConnectionClass dbConnectionClass = new DBConnectionClass();
    Connection DBConnection = dbConnectionClass.getConnection();
    @FXML
    private Button btnMainMenu;
    @FXML
    private TextField txtEmpIDDelete;
    @FXML
    private TextField txtEmpIDSearch;
    @FXML
    private TextField txtEmpIDView;
    @FXML
    private TextField txtEmpID;
    @FXML
    private TextField txtEmpFName;
    @FXML
    private TextField txtEmpLName;
    @FXML
    private DatePicker dateEmpDOB;
    @FXML
    private TextField txtEmpNumber;
    @FXML
    private TextArea txtEmpAddress;
    @FXML
    private Label lblEmpIDUp;
    @FXML
    private TextField txtEmpFNameUp;
    @FXML
    private TextField txtEmpLNameUp;
    @FXML
    private DatePicker dateEmpDOBUp;
    @FXML
    private TextField txtEmpNumberUp;
    @FXML
    private TextArea txtEmpAddressUp;
    @FXML
    private TextField txtEmpIDJobAssign;
    @FXML
    private TextField txtRIDJobAssign;
    @FXML
    private TextField txtHoursJobAssign;
    @FXML
    private TextField txtEmpIDRoleDel;
    @FXML
    private TextField txtRoleIDRoleDel;
    @FXML
    private TextField txtEmpIDRoleView;
    @FXML
    private TextField txtEmpIDWork;
    @FXML
    private TextField txtConIDWork;
    @FXML
    private DatePicker dateRegWork;
    @FXML
    private TextField txtEmpIDViewWork;
    @FXML
    private TextField txtEmpIDPay;
    @FXML
    private Label lblEmpID;
    @FXML
    private Label lblEmpFName;
    @FXML
    private Label lblEmpLName;
    @FXML
    private Label lblEmpDOB;
    @FXML
    private Label lblEmpNumber;
    @FXML
    private Label lblEmpAddress;
    @FXML
    private Label lblEmpIDDel;
    @FXML
    private Label lblEmpFNameDel;
    @FXML
    private Label lblEmpLNameDel;
    @FXML
    private Label lblEmpDOBDel;
    @FXML
    private Label lblEmpNumberDel;
    @FXML
    private Label lblEmpAddressDel;
    @FXML
    private Label lblEmpIDRoleDel;
    @FXML
    private Label lblRIDEmpRoleDel;
    @FXML
    private Label lblHoursEmpRoleDel;
    @FXML
    private Label lblEmpIDRole;
    @FXML
    private Label lblRIDEmpRole;
    @FXML
    private Label lblHoursWorkedEmpRole;
    @FXML
    private Label lblEmpIDWork;
    @FXML
    private Label lblConIDWork;
    @FXML
    private Label lblDateRegisteredWork;
    @FXML

    private Label lblHourlyPayPay;
    @FXML
    private Label lblEmpIDPay;
    @FXML
    private Label lblRoleIDPay;
    @FXML
    private Label lblHoursWorkedPay;
    @FXML
    private Label lblDisplayPayEmployee;
    // Declaring and assigning variables to hold the information
    // ,regarding Employee, retrieved from the program
    // before passing on to the database
    private int EmpID;
    private String EmpFName;
    private String EmpLName;
    private LocalDate EmpDOB;
    private int EmpNumber;
    private String EmpAddress;
    private int employeeCreateValue = 0;
    private int employeeUpdateValue = 0;
    // Declaring and assigning variables to hold the information
    // ,regarding Employee Job Role, retrieved from the program
    // before passing on to the database
    private int JobRoleEmpID;
    private int JobRoleID;
    private String JobRoleIDSearch;
    private Double JobRoleHoursWorked;
    private String JobRoleHoursWorkedSearch;
    private int jobRoleAssignValue = 0;
    // Declaring and assigning variables to hold the information
    // ,regarding Work, retrieved from the program
    // before passing on to the database
    private int WorkEmpID;
    private int WorkConID;
    private LocalDate WorkDateRegistered;
    private String WorkConIDSearch;
    private String WorkDateRegisteredSearch;
    private int workAssignValue = 0;
    private int PayEmpID;
    private String PayRoleID;
    private String PayHoursWorked;
    private String PayHourlyPay;
    private double TotalPay = 0;
    private double hoursWorkedCal;
    private double hourlyPayCal;

    /**
     * This method launches onclick of the Main Menu button in the ManageEmployee.fxml
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
     * This method launches onclick of the Create Employee button of the
     * Create Employee tab in the ManageEmployee.fxml
     */
    public void createEmployee(ActionEvent evt) {
        try {
            if ((txtEmpFName.getText()).length() == 0 || (txtEmpLName.getText()).length() == 0 ||
                    (dateEmpDOB.getValue()) == null || (txtEmpAddress.getText()).length() == 0) {
                employeeCreateValue = 0;
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
            } else if ((txtEmpNumber.getText()).length() != 10) {
                employeeCreateValue = 0;
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
                } catch (IOException exception) {
                    System.out.println("This file does not exist");
                }
            } else {
                EmpID = Integer.parseInt(txtEmpID.getText());
                EmpFName = txtEmpFName.getText();
                EmpLName = txtEmpLName.getText();
                EmpDOB = dateEmpDOB.getValue();
                EmpNumber = Integer.parseInt(txtEmpNumber.getText());
                EmpAddress = txtEmpAddress.getText();
                employeeCreateValue = 1;
            }
        } catch (NumberFormatException e) {
            employeeCreateValue = 0;
            try {
                ControllerAlertBox.alertHeader = "Input Mismatch";
                ControllerAlertBox.alertMessage = "Please enter numeric values for ID and " +
                        "contact number";
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

        if (employeeCreateValue == 1) {
            try {
                try {
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
                if (ControllerConfirmationBox.confirmValue == 1) {
                    String sqlEmployee = "INSERT INTO computer_consultancy_firm.employee(eID, " +
                            "eFirstName, eLastName, " +
                            "eDOB, eNumber, eAddress) VALUES(" +
                            "" + EmpID + ",'" + EmpFName + "','" + EmpLName + "'," +
                            "'" + EmpDOB + "'," + EmpNumber + ",'" + EmpAddress + "')";
                    Statement SQLStatement = DBConnection.createStatement();
                    SQLStatement.executeUpdate(sqlEmployee);
                    try {
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
     * This method launches onclick of the Search Employee button of the
     * Delete Employee tab in the ManageEmployee.fxml
     */
    public void searchDeleteEmployee(ActionEvent evt) {
        try {
            String sqlEmployee = "SELECT * FROM computer_consultancy_firm.employee WHERE eID=" +
                    "('" + Integer.parseInt(txtEmpIDDelete.getText()) + "')";
            Statement SQLStatement = DBConnection.createStatement();
            ResultSet resultSetSQL = SQLStatement.executeQuery(sqlEmployee);
            while (resultSetSQL.next()) {
                lblEmpIDDel.setText(String.valueOf(resultSetSQL.getInt("eID")));
                lblEmpFNameDel.setText(resultSetSQL.getString("eFirstName"));
                lblEmpLNameDel.setText(resultSetSQL.getString("eLastName"));
                lblEmpDOBDel.setText(String.valueOf(resultSetSQL.getDate("eDOB")));
                lblEmpNumberDel.setText(String.valueOf(resultSetSQL.getInt("eNumber")));
                lblEmpAddressDel.setText(resultSetSQL.getString("eAddress"));
            }
        } catch (SQLException e) {
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
     * This method launches onclick of the Delete Employee button of the
     * Delete Employee tab in the ManageEmployee.fxml
     */
    public void deleteEmployee(ActionEvent evt) {
        try {
            try {
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
            if (ControllerConfirmationBox.confirmValue == 1) {
                String sqlEmployee = "DELETE FROM computer_consultancy_firm.employee WHERE eID=" +
                        "('" + lblEmpIDDel.getText() + "')";
                Statement SQLStatement = DBConnection.createStatement();
                SQLStatement.executeUpdate(sqlEmployee);
                try {
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
     * This method launches onclick of the Search Employee button of the
     * Update Employee tab in the ManageEmployee.fxml
     */
    public void searchUpdateEmployee(ActionEvent evt) {
        try {
            String sqlEmployee = "SELECT * FROM computer_consultancy_firm.employee WHERE eID=" +
                    "('" + Integer.parseInt(txtEmpIDSearch.getText()) + "')";
            Statement SQLStatement = DBConnection.createStatement();
            ResultSet resultSetSQL = SQLStatement.executeQuery(sqlEmployee);
            while (resultSetSQL.next()) {
                lblEmpIDUp.setText(String.valueOf(resultSetSQL.getInt("eID")));
                txtEmpFNameUp.setText(resultSetSQL.getString("eFirstName"));
                txtEmpLNameUp.setText(resultSetSQL.getString("eLastName"));
                dateEmpDOBUp.setValue((resultSetSQL.getDate("eDOB")).toLocalDate());
                txtEmpNumberUp.setText(String.valueOf(resultSetSQL.getInt("eNumber")));
                txtEmpAddressUp.setText(resultSetSQL.getString("eAddress"));
            }
        } catch (SQLException e) {
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
     * This method launches onclick of the Update Employee button of the
     * Update Employee tab in the ManageEmployee.fxml
     */
    public void updateEmployee(ActionEvent evt) {
        try {
            if ((txtEmpFNameUp.getText()).length() == 0 || (txtEmpLNameUp.getText()).length() == 0 ||
                    (dateEmpDOBUp.getValue()) == null || (txtEmpAddressUp.getText()).length() == 0) {
                employeeUpdateValue = 0;
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
            } else if ((txtEmpNumberUp.getText()).length() != 10) {
                employeeUpdateValue = 0;
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
                } catch (IOException exception) {
                    System.out.println("This file does not exist");
                }
            } else {
                EmpID = Integer.parseInt(lblEmpIDUp.getText());
                EmpFName = txtEmpFNameUp.getText();
                EmpLName = txtEmpLNameUp.getText();
                EmpDOB = dateEmpDOBUp.getValue();
                EmpNumber = Integer.parseInt(txtEmpNumberUp.getText());
                EmpAddress = txtEmpAddressUp.getText();
                employeeUpdateValue = 1;
            }
        } catch (NumberFormatException e) {
            employeeUpdateValue = 0;
            try {
                ControllerAlertBox.alertHeader = "Input Mismatch";
                ControllerAlertBox.alertMessage = "Please enter numeric values for ID and " +
                        "contact number";
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

        if (employeeUpdateValue == 1) {
            try {
                try {
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
                if (ControllerConfirmationBox.confirmValue == 1) {
                    String sqlEmployee = "UPDATE computer_consultancy_firm.employee SET" +
                            " eFirstName = '" + EmpFName + "'," +
                            " eLastName = '" + EmpLName + "'," +
                            " eDOB = '" + EmpDOB + "'," +
                            " eNumber = '" + EmpNumber + "'," +
                            " eAddress = '" + EmpAddress + "'" +
                            " WHERE eID = " + EmpID + ";";
                    Statement SQLStatement = DBConnection.createStatement();
                    SQLStatement.executeUpdate(sqlEmployee);
                    try {
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
     * This method launches onclick of the View Employee button of the
     * View Employee tab in the ManageEmployee.fxml
     */
    public void searchViewEmployee(ActionEvent evt) {
        try {
            String sqlEmployee = "SELECT * FROM computer_consultancy_firm.employee WHERE eID=" +
                    "('" + Integer.parseInt(txtEmpIDView.getText()) + "')";
            Statement SQLStatement = DBConnection.createStatement();
            ResultSet resultSetSQL = SQLStatement.executeQuery(sqlEmployee);
            while (resultSetSQL.next()) {
                lblEmpID.setText(String.valueOf(resultSetSQL.getInt("eID")));
                lblEmpFName.setText(resultSetSQL.getString("eFirstName"));
                lblEmpLName.setText(resultSetSQL.getString("eLastName"));
                lblEmpDOB.setText(String.valueOf((resultSetSQL.getDate("eDOB")).toLocalDate()));
                lblEmpNumber.setText(String.valueOf(resultSetSQL.getInt("eNumber")));
                lblEmpAddress.setText(resultSetSQL.getString("eAddress"));
            }
        } catch (SQLException e) {
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
     * This method launches onclick of the Assign Job Role button of the
     * Job Role/Assign Job Role tab in the ManageEmployee.fxml
     */
    public void assignJobRole(ActionEvent evt) {
        try {
            if ((txtEmpIDJobAssign.getText()).length() == 0 || (txtRIDJobAssign.getText()).length() == 0 ||
                    (txtHoursJobAssign.getText()).length() == 0) {
                jobRoleAssignValue = 0;
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
                JobRoleEmpID = Integer.parseInt(txtEmpIDJobAssign.getText());
                JobRoleID = Integer.parseInt(txtRIDJobAssign.getText());
                JobRoleHoursWorked = Double.valueOf(txtHoursJobAssign.getText());
                jobRoleAssignValue = 1;
            }
        } catch (NumberFormatException e) {
            jobRoleAssignValue = 0;
            try {
                ControllerAlertBox.alertHeader = "Input Mismatch";
                ControllerAlertBox.alertMessage = "Please enter numeric values for IDs and " +
                        "Hours Worked.";
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

        if (jobRoleAssignValue == 1) {
            try {
                try {
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
                if (ControllerConfirmationBox.confirmValue == 1) {
                    String sqlEmployee = "INSERT INTO computer_consultancy_firm.employee_job_role" +
                            "(eID, rID, " +
                            "hoursWorked) VALUES(" +
                            "" + JobRoleEmpID + ",'" + JobRoleID + "','" + JobRoleHoursWorked + "')";
                    Statement SQLStatement = DBConnection.createStatement();
                    SQLStatement.executeUpdate(sqlEmployee);
                    try {
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
     * This method launches onclick of the Search Job Role button of the
     * Job Role/Delete Job Role tab in the ManageEmployee.fxml
     */
    public void searchDeleteJobRole(ActionEvent evt) {
        try {
            if ((txtEmpIDRoleDel.getText()).length() == 0 || (txtRoleIDRoleDel.getText()).length() == 0) {
                try {
                    ControllerAlertBox.alertHeader = "Invalid data!";
                    ControllerAlertBox.alertMessage = "Please fill in both the text fields.";
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
                String sqlEmployee = "SELECT * FROM computer_consultancy_firm.employee_job_role " +
                        "WHERE eID=" +
                        "('" + Integer.parseInt(txtEmpIDRoleDel.getText()) + "') AND rID=" +
                        "('" + Integer.parseInt(txtRoleIDRoleDel.getText()) + "')";
                Statement SQLStatement = DBConnection.createStatement();
                ResultSet resultSetSQL = SQLStatement.executeQuery(sqlEmployee);
                while (resultSetSQL.next()) {
                    lblEmpIDRoleDel.setText(String.valueOf(resultSetSQL.getInt("eID")));
                    lblRIDEmpRoleDel.setText(String.valueOf(resultSetSQL.getInt("rID")));
                    lblHoursEmpRoleDel.setText(String.valueOf(resultSetSQL.getDouble(
                            "hoursWorked")));
                }
            }
        } catch (SQLException e) {
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
     * This method launches onclick of the Delete Job Role button of the
     * Job Role/Delete Job Role tab in the ManageEmployee.fxml
     */
    public void deleteJobRole(ActionEvent evt) {
        try {
            try {
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
            if (ControllerConfirmationBox.confirmValue == 1) {
                String sqlEmployee = "DELETE FROM computer_consultancy_firm.employee_job_role " +
                        "WHERE eID=" +
                        "('" + lblEmpIDRoleDel.getText() + "') AND rID=" +
                        "('" + lblRIDEmpRoleDel.getText() + "');";
                Statement SQLStatement = DBConnection.createStatement();
                SQLStatement.executeUpdate(sqlEmployee);
                try {
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
     * This method launches onclick of the View Job Role button of the
     * Job Role/View Job Role tab in the ManageEmployee.fxml
     */
    public void searchViewJobRole(ActionEvent evt) {
        try {
            JobRoleIDSearch = "";
            JobRoleHoursWorkedSearch = "";
            String sqlEmployee = "SELECT * FROM computer_consultancy_firm.employee_job_role WHERE" +
                    " eID=" +
                    "('" + Integer.parseInt(txtEmpIDRoleView.getText()) + "')";
            Statement SQLStatement = DBConnection.createStatement();
            ResultSet resultSetSQL = SQLStatement.executeQuery(sqlEmployee);
            while (resultSetSQL.next()) {
                JobRoleEmpID = resultSetSQL.getInt("eID");
                JobRoleIDSearch += resultSetSQL.getInt("rID");
                JobRoleHoursWorkedSearch += resultSetSQL.getDouble("hoursWorked");

                JobRoleIDSearch += "\n";
                JobRoleHoursWorkedSearch += "\n";
            }
            lblEmpIDRole.setText(String.valueOf(JobRoleEmpID));
            lblRIDEmpRole.setText(JobRoleIDSearch);
            lblHoursWorkedEmpRole.setText(String.valueOf(JobRoleHoursWorkedSearch));
        } catch (SQLException e) {
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
     * This method launches onclick of the Assign Work button of the
     * Work/Assign Work tab in the ManageEmployee.fxml
     */
    public void assignWork(ActionEvent evt) {
        try {
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
                WorkEmpID = Integer.parseInt(txtEmpIDWork.getText());
                WorkConID = Integer.parseInt(txtConIDWork.getText());
                WorkDateRegistered = dateRegWork.getValue();
                workAssignValue = 1;
            }
        } catch (NumberFormatException e) {
            workAssignValue = 0;
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

        if (workAssignValue == 1) {
            try {
                try {
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
                if (ControllerConfirmationBox.confirmValue == 1) {
                    String sqlEmployee = "INSERT INTO computer_consultancy_firm.employee_work" +
                            "(eID, conID, " +
                            "dateRegistered) VALUES(" +
                            "" + WorkEmpID + ",'" + WorkConID + "','" + WorkDateRegistered + "')";
                    Statement SQLStatement = DBConnection.createStatement();
                    SQLStatement.executeUpdate(sqlEmployee);
                    try {
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
     * Work/View Work tab in the ManageEmployee.fxml
     */
    public void searchViewWork(ActionEvent evt) {
        try {
            WorkConIDSearch = "";
            WorkDateRegisteredSearch = "";
            String sqlEmployee = "SELECT * FROM computer_consultancy_firm.employee_work WHERE " +
                    "eID=" +
                    "('" + Integer.parseInt(txtEmpIDViewWork.getText()) + "')";
            Statement SQLStatement = DBConnection.createStatement();
            ResultSet resultSetSQL = SQLStatement.executeQuery(sqlEmployee);
            while (resultSetSQL.next()) {
                WorkEmpID = resultSetSQL.getInt("eID");
                WorkConIDSearch += resultSetSQL.getInt("conID");
                WorkDateRegisteredSearch += resultSetSQL.getDate("dateRegistered");
                WorkConIDSearch += "\n";
                WorkDateRegisteredSearch += "\n";
            }
            lblEmpIDWork.setText(String.valueOf(WorkEmpID));
            lblConIDWork.setText(String.valueOf(WorkConIDSearch));
            lblDateRegisteredWork.setText(String.valueOf(WorkDateRegisteredSearch));
        } catch (SQLException e) {
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
     * This method launches onclick of the Calculate Pay button of the
     * Calculate Pay tab in the ManageEmployee.fxml
     */
    public void calculatePay(ActionEvent evt) {
        try {
            PayRoleID = "";
            PayHoursWorked = "";
            PayHourlyPay = "";
            TotalPay = 0;
            String sqlEmployee = "SELECT employee_job_role.eID, employee_job_role.rID, " +
                    "employee_job_role.hoursWorked, role.rHourlyPay " +
                    "FROM computer_consultancy_firm.employee_job_role INNER JOIN " +
                    "computer_consultancy_firm.role " +
                    "USING (rID) WHERE eID = " +
                    "('" + Integer.parseInt(txtEmpIDPay.getText()) + "')";
            Statement SQLStatement = DBConnection.createStatement();
            ResultSet resultSetSQL = SQLStatement.executeQuery(sqlEmployee);
            while (resultSetSQL.next()) {
                PayEmpID = resultSetSQL.getInt("eID");
                PayRoleID += resultSetSQL.getInt("rID");
                PayHoursWorked += resultSetSQL.getDouble("hoursWorked");
                PayHourlyPay += resultSetSQL.getDouble("rHourlyPay");

                hoursWorkedCal = resultSetSQL.getDouble("hoursWorked");
                hourlyPayCal = resultSetSQL.getDouble("rHourlyPay");
                TotalPay += hoursWorkedCal * hourlyPayCal;

                PayRoleID += "\n";
                PayHoursWorked += "\n";
                PayHourlyPay += "\n";
            }
            lblEmpIDPay.setText(String.valueOf(PayEmpID));
            lblRoleIDPay.setText(String.valueOf(PayRoleID));
            lblHoursWorkedPay.setText(String.valueOf(PayHoursWorked));
            lblHourlyPayPay.setText(String.valueOf(PayHourlyPay));
            lblDisplayPayEmployee.setText(String.valueOf(TotalPay));
        } catch (SQLException e) {
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
}
