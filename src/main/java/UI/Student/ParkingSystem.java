package UI.Student;

import Service.StudentService;
import UI.Staff.MainMenu.InputController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Student;

import java.io.IOException;

public class ParkingSystem extends Application {
    private static Tab dashboardTab;
    private static Tab loginTab;
    private static TabPane tabPane;
    private Label announce;// Biến này phải là static
    private StudentDashboard studentDashboard=new StudentDashboard();

    @Override
    public void start(Stage primaryStage) {
        tabPane = new TabPane();  // Cập nhật lại thành biến static

        // Login Tab
        loginTab = new Tab("Login");
        loginTab.setClosable(false);
        VBox loginBox = createLoginScreen();
        loginTab.setContent(loginBox);

        // Dashboard Tab
        dashboardTab = new Tab("Dashboard");
        dashboardTab.setClosable(false);
        dashboardTab.setDisable(true); // Disable initially

        // Add tabs to TabPane
        tabPane.getTabs().addAll(loginTab, dashboardTab);

        // Login button action
        Button loginButton = (Button) loginBox.lookup(".button");
        loginButton.setOnAction(event -> {
            // Simple login check
            TextField usernameField = (TextField) loginBox.lookup(".text-field");
            PasswordField passwordField = (PasswordField) loginBox.lookup(".password-field");
            StudentService studentService = new StudentService();
            try{
                if (studentService.checklogin(Integer.parseInt(usernameField.getText()), passwordField.getText())) {
                    // Enable the other tabs and switch to Dashboard tab
                    this.studentDashboard=new StudentDashboard(usernameField.getText());
                    dashboardTab.setContent(this.studentDashboard.createDashboard());
                    dashboardTab.setDisable(false);
                    tabPane.getSelectionModel().select(dashboardTab); // Switch to Dashboard tab
                } else {
                    // Show error message if login is invalid
                    this.announce.setText("Please enter both username and password.");
                }
            } catch (Exception e) {
                this.announce.setText("Invalid User");
            }
        });

        // Scene setup
        Scene scene = new Scene(tabPane, 800, 600);
        primaryStage.setTitle("Student Parking System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createLoginScreen() {
        VBox loginBox = new VBox(20);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(50));
        loginBox.setStyle("-fx-background-color: #ff3b30;");

        // Login panel
        VBox loginPanel = new VBox(15);
        loginPanel.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        loginPanel.setPadding(new Insets(30));
        loginPanel.setMaxWidth(400);

        Label titleLabel = new Label("Student Login");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-background-radius: 5;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-radius: 5;");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #ff3b30; -fx-text-fill: white; -fx-background-radius: 5;");
        loginButton.setPrefWidth(200);

        Hyperlink forgotPassword = new Hyperlink("Forgot Password?");
        forgotPassword.setOnAction(event -> {
            StudentService studentService = new StudentService();
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

        });
        //Hyperlink register = new Hyperlink("Register");
        this.announce = new Label("");

        loginPanel.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton, forgotPassword,this.announce);
        loginPanel.setAlignment(Pos.CENTER);

        loginBox.getChildren().add(loginPanel);

        return loginBox;
    }

    // Phương thức đăng xuất
    public static void showLoginScreen() {
        // Đảm bảo rằng các tab khác đều bị vô hiệu hóa
        dashboardTab.setDisable(true);

        // Chọn lại tab login
        tabPane.getSelectionModel().select(loginTab);
    }

    public static void main(String[] args) {
        launch();
    }
}
