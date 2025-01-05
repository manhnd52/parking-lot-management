package UI.Staff.Login;

import Service.StaffService;
import UI.Staff.MainMenu.InputController;
import UI.Staff.MainMenu.MainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {
    private StaffService staffService=new StaffService();

    @FXML
    private TextField usernameTXT;
    @FXML
    private TextField passwordPWF;
    @FXML
    private Label announceLB;

    @FXML
    void loginBtnpressed(ActionEvent event) {
        try{
            String username=usernameTXT.getText();
            String password=passwordPWF.getText();
            if(staffService.checklogin(Integer.parseInt(username),password)) {
                Stage stage = (Stage) usernameTXT.getScene().getWindow();
                MainMenuController mainMenuController=new MainMenuController(Integer.parseInt(username));
                FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/UI/Staff/MainMenu/MainMenu.fxml"));
                fxmlLoader.setController(mainMenuController);
                Parent root = null;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(root);
                stage.setTitle("MainMenu");
                stage.setScene(scene);
                stage.show();

            }
            else announceLB.setText("Login Failed");
        } catch (Exception e) {
            announceLB.setText("Hãy nhập thông tin");
        }

    }
    @FXML
    void forgotBtnpressed(ActionEvent event) {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("ChangePassword.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("ChangPassword");
        stage.setScene(scene);
        stage.show();

    }


}
