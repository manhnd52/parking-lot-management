package UI.Staff.Login;

import Service.StaffService;
import com.dlsc.formsfx.model.structure.PasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class ChangePasswordController {
    private StaffService staffService=new StaffService();

    @FXML
    private TextField staffidTXT;
    @FXML
    private TextField newpassPWF;
    @FXML
    private TextField confirmpassPWF;

    @FXML
    void updatepassBTN(ActionEvent event) {
        if(staffService.changePassword(Integer.parseInt(staffidTXT.getText()),
                newpassPWF.getText(),confirmpassPWF.getText())) announceLB.setText("Thay đổi thành công");
        else announceLB.setText("Thay đổi thất bại");
    }

    @FXML
    private Label announceLB;



}
