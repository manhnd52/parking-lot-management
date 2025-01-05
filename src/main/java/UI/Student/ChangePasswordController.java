package UI.Student;


import Service.StudentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class ChangePasswordController {
    private StudentService studentService=new StudentService();

    @FXML
    private TextField studentidTXT;
    @FXML
    private TextField newpassPWF;
    @FXML
    private TextField confirmpassPWF;

    @FXML
    void updatepassBTN(ActionEvent event) {
        if(studentService.changePassword(studentidTXT.getText(),
                newpassPWF.getText(),confirmpassPWF.getText())) announceLB.setText("Thay đổi thành công");
        else announceLB.setText("Thay đổi thất bại");
    }

    @FXML
    private Label announceLB;



}
