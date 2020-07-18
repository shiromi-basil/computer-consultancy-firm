package code;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This controller manages the verification of user login.
 *
 * @author Shiromi Basil
 * @since 2019-04-05
 */
public class ControllerLogin {
    // Setting the username and the password of the program
    public static final String USERNAME = "Admin";
    public static final String PASSWORD = "1234";
    @FXML
    private Button btnLogin;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblPassword;

    /**
     * This method launches onclick of the Login button in the Login.fxml
     */
    public void systemLogin(ActionEvent evt) {
        // Retrieving the username and password entered by the user
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // Checking if the entered username password is correct
        if ((username.equals(USERNAME)) && (password.equals(PASSWORD))) {
            // If the username and password is correct then the DBConnection.fxml is launched
            // to connect the database to the program
            try {
                Parent parentNode =
                        FXMLLoader.load(getClass().getResource("../design/DBConnection" +
                                ".fxml"));
                Stage openStage = new Stage();
                openStage.setTitle("Database Connection");
                openStage.setScene(new Scene(parentNode));
                openStage.show();
                Stage closeStage = (Stage) btnLogin.getScene().getWindow();
                closeStage.close();

            } catch (IOException e) {
                System.out.println("This file does not exist");
            }
            // In case of wrong username or password, error message must be displayed
        } else {
            if ((username.length() == 0) && (password.length() == 0)) {
                // In case of empty username and password fields
                lblUsername.setText("Please enter username");
                lblPassword.setText("Please enter password");
            } else if (username.length() == 0) {
                // In case of empty username field
                lblUsername.setText("Please enter username");
                lblPassword.setText("");
            } else if (password.length() == 0) {
                // In case of empty password field
                lblUsername.setText("");
                lblPassword.setText("Please enter password");
            } else {
                // In case of incorrect username or password
                lblUsername.setText("");
                lblPassword.setText("Incorrect username or password.");
            }
        }

    }

    /**
     * This method launches onclick of the Exit button in the Login.fxml
     */
    public void systemExit(ActionEvent evt) {
        System.exit(0);
    }
}
