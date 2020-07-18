package code;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * This controller manages the information displayed on the Confirmation Box and the action of
 * the OK and Cancel buttons.
 *
 * @author Shiromi Basil
 * @since 2019-04-05
 */
public class ControllerConfirmationBox {
    @FXML
    private Button btnConfirmOK;
    @FXML
    private Button btnConfirmCancel;
    @FXML
    private Label lblConfirmMsg;

    // Setting message of the alert box initially to null
    // and setting the confirmValue (the value returned to the called method to verify the
    // button clicked by the user) to zero
    public static String confirmMsg = "";
    public static int confirmValue = 0;

    /**
     * This method launches as soon this AlertBox.fxml is loaded
     */
    public void initialize() {
        lblConfirmMsg.setText(confirmMsg);
    }

    /**
     * This method launches onclick of the OK button in the AlertBox.fxml
     */
    public void confirmOK(ActionEvent evt) {
        confirmValue = 1;
        Stage closeStage = (Stage) btnConfirmOK.getScene().getWindow();
        closeStage.close();
    }

    /**
     * This method launches onclick of the Cancel button in the AlertBox.fxml
     */
    public void confirmCancel(ActionEvent evt) {
        confirmValue = 0;
        Stage closeStage = (Stage) btnConfirmCancel.getScene().getWindow();
        closeStage.close();
    }
}

