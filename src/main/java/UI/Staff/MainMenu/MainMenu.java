package UI.Staff.MainMenu;

import UI.Staff.Login.LoginMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainMenu extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        MainMenuController mainMenuController=new MainMenuController(155);
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        fxmlLoader.setController(mainMenuController);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("MainMenu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
