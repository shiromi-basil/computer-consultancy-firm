package code;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This controller manages buttons of the main menu.
 *
 * @author Shiromi Basil
 * @since 2019-04-05
 */
public class ControllerMainMenu {
    @FXML
    private Label lblGreeting;
    @FXML
    private Button btnManageEmployee;
    @FXML
    private Button btnManageCustomer;
    @FXML
    private Button btnManageContract;
    @FXML
    private Button btnManageRole;
    @FXML
    private Button btnLogOut;

    /**
     * This method launches as soon this MainMenu.fxml is loaded
     * displaying the greeting message to the user.
     */
    public void initialize() {
        lblGreeting.setText("Welcome " + ControllerLogin.USERNAME + "!");
    }

    /**
     * This method launches onclick of the Manage Employee button in the MainMenu.fxml
     */
    public void employeeManage(ActionEvent evt) {
        try {
            Parent parentNode =
                    FXMLLoader.load(getClass().getResource("../design/ManageEmployee.fxml"));
            Stage openStage = new Stage();
            openStage.setTitle("Manage Employee");
            openStage.setScene(new Scene(parentNode));
            openStage.show();
        } catch (IOException e) {

            System.out.println("This file does not exist");
        }
        Stage closeStage = (Stage) btnManageEmployee.getScene().getWindow();
        closeStage.close();

    }

    /**
     * This method launches onclick of the Manage Customer button in the MainMenu.fxml
     */
    public void customerManage(ActionEvent evt) {
        try {
            Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                    "/ManageCustomer.fxml"));
            Stage openStage = new Stage();
            openStage.setTitle("Manage Customer");
            openStage.setScene(new Scene(parentNode));
            openStage.show();
        } catch (IOException e) {
            System.out.println("This file does not exist");
        }
        Stage closeStage = (Stage) btnManageCustomer.getScene().getWindow();
        closeStage.close();

    }

    /**
     * This method launches onclick of the Manage Contract button in the MainMenu.fxml
     */
    public void contractManage(ActionEvent evt) {
        try {
            Parent parentNode = FXMLLoader.load(getClass().getResource("../Design" +
                    "/ManageContract.fxml"));
            Stage openStage = new Stage();
            openStage.setTitle("Manage Contract");
            openStage.setScene(new Scene(parentNode));
            openStage.show();
        } catch (IOException e) {
            System.out.println("This file does not exist");
        }
        Stage closeStage = (Stage) btnManageContract.getScene().getWindow();
        closeStage.close();

    }

    /**
     * This method launches onclick of the Manage Role button in the MainMenu.fxml
     */
    public void roleManage(ActionEvent evt) {
        try {
            Parent parentNode = FXMLLoader.load(getClass().getResource("../Design/ManageRole" +
                    ".fxml"));
            Stage openStage = new Stage();
            openStage.setTitle("Manage Role");
            openStage.setScene(new Scene(parentNode));
            openStage.show();
        } catch (IOException e) {
            System.out.println("This file does not exist");
        }
        Stage closeStage = (Stage) btnManageRole.getScene().getWindow();
        closeStage.close();

    }

    /**
     * This method launches onclick of the Log out button in the MainMenu.fxml
     */
    public void systemLogout(ActionEvent evt) {
        try {
            Parent parentNode = FXMLLoader.load(getClass().getResource("../Design/Login" +
                    ".fxml"));
            Stage openStage = new Stage();
            openStage.setTitle("Login");
            openStage.setScene(new Scene(parentNode));
            openStage.show();
        } catch (IOException e) {
            System.out.println("This file does not exist");
        }
        Stage closeStage = (Stage) btnLogOut.getScene().getWindow();
        closeStage.close();

    }
}
