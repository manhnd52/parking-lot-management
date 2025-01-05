package UI.Staff.MainMenu;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Input extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        InputController inputController=new InputController(9);
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("Input.fxml"));
        fxmlLoader.setController(inputController);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Input");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
