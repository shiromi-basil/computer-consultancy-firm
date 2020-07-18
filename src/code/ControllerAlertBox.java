package code;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * This controller manages the information displayed on the Alert Box.
 *
 * @author Shiromi Basil
 * @since 2019-04-05
 */
public class ControllerAlertBox {
    @FXML
    private Button btnAlert;
    @FXML
    private Label lblAlertHeader;
    @FXML
    private Label lblAlertMsg;

    // Setting the header and the message of the alert box initially to null
    public static String alertHeader = "";
    public static String alertMessage = "";

    /**
     * This method launches as soon this AlertBox.fxml is loaded
     */
    public void initialize() {
        lblAlertHeader.setText(alertHeader);
        lblAlertMsg.setText(alertMessage);
    }

    /**
     * This method launches onclick of the OK button in the AlertBox.fxml
     */
    public void confirmAlert(ActionEvent evt) {
        // Closes the current open FXML file
        Stage closeStage = (Stage) btnAlert.getScene().getWindow();
        closeStage.close();
    }
}
