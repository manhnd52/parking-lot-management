package UI.Student;


import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;

public class StudentDashboard {
    private String student_id;

    public StudentDashboard(String student_id) {
        this.student_id = student_id;
    }

    public StudentDashboard(){}

    public BorderPane createDashboard() {
        BorderPane dashboard = new BorderPane();
        dashboard.setPadding(new Insets(20));
        dashboard.setStyle("-fx-background-color: #f5f5f5;");

        // Top header
        HBox header = createHeader();
        dashboard.setTop(header);

        // Main content
        GridPane mainContent = createMainContent();
        dashboard.setCenter(mainContent);

        return dashboard;
    }

    public HBox createHeader() {
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 20, 0));
        header.setStyle("-fx-background-color: white; -fx-padding: 15px; -fx-background-radius: 10;");

        // Avatar
        Circle avatar = new Circle(25);
        avatar.setFill(Color.LIGHTGRAY);

        // User info
        VBox userInfo = new VBox(5);
        Label nameLabel = new Label("Welcome Student!");
        Label idLabel = new Label("ID:"+this.student_id);
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        userInfo.getChildren().addAll(nameLabel, idLabel);

        // Logout button
        Button logoutButton = new Button("Đăng xuất");
        logoutButton.setStyle("-fx-background-color: #FF6347; -fx-text-fill: white;");
        logoutButton.setOnAction(e -> handleLogout());

        // Add everything to header
        header.getChildren().addAll(avatar, userInfo, logoutButton);
        return header;
    }

    private static void handleLogout() {

        // Mở lại cửa sổ Login trong ParkingSystem
        ParkingSystem.showLoginScreen();  // Phương thức mở màn hình Login từ ParkingSystem
    }

    private GridPane createMainContent() {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);

        // Menu items
        VBox settings = createMenuItem("Settings", "⚙️");
        VBox parkingHistory = createMenuItem("Parking History", "📋");
        VBox personalInfo = createMenuItem("Personal Information", "👤");
        VBox newBooking = createMenuItem("New Booking", "🚗");
        VBox payment = createMenuItem("Payment", "💳");

        grid.add(settings, 0, 0);
        grid.add(parkingHistory, 1, 0);
        grid.add(personalInfo, 2, 0);
        grid.add(newBooking, 0, 1);
        grid.add(payment, 1, 1);

        // Add click event to each menu item to switch scenes
        addMenuItemEvent(settings, "Settings");
        addMenuItemEvent(parkingHistory, "ParkingHistory");
        addMenuItemEvent(personalInfo, "PersonalInfo");
        addMenuItemEvent(newBooking, "NewBooking");
        addMenuItemEvent(payment, "Payment");

        return grid;
    }

    private static VBox createMenuItem(String title, String icon) {
        VBox item = new VBox(10);
        item.setPadding(new Insets(20));
        item.setAlignment(Pos.CENTER);
        item.setPrefSize(200, 150);
        item.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");

        Label iconLabel = new Label(icon);
        iconLabel.setFont(Font.font("System", 30));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        item.getChildren().addAll(iconLabel, titleLabel);

        // Hover effect
        item.setOnMouseEntered(e -> item.setStyle("-fx-background-color: #f8f8f8; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 4);"));
        item.setOnMouseExited(e -> item.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"));

        return item;
    }

    // Handle click event for each menu item
    private void addMenuItemEvent(VBox menuItem, String sceneName) {
        menuItem.setOnMouseClicked(event -> {
            switch (sceneName) {
                case "Settings":
                    showSettingsScene();
                    break;
                case "ParkingHistory":
                    showParkingHistoryScene();
                    break;
                case "PersonalInfo":
                    showPersonalInfoScene();
                    break;
                case "NewBooking":
                    showNewBookingScene();
                    break;
                case "Payment":
                    showPaymentScene();
                    break;
                default:
                    break;
            }
        });
    }

    // Dummy methods to handle each scene
    private static void showSettingsScene() {
        // Navigate to the Settings screen
        System.out.println("Navigating to Settings...");
        // You can implement the navigation logic here
    }

    private void showParkingHistoryScene() {
        // Tạo một cửa sổ mới (Stage) để hiển thị màn hình Parking History
        Stage stage = new Stage();

        // Lấy giao diện từ class ParkingHistory
        VBox parkingHistoryScreen = new ParkingHistory(this.student_id).createParkingHistoryScreen(); // Lấy màn hình Parking History từ class ParkingHistory

        // Tạo một Scene mới với giao diện Parking History
        Scene parkingHistoryScene = new Scene(parkingHistoryScreen, 600, 400); // Thay đổi kích thước nếu cần

        // Đặt Scene vào Stage và hiển thị Stage
        stage.setTitle("Parking History");
        stage.setScene(parkingHistoryScene);
        stage.show();
    }

    private static void showPersonalInfoScene() {
        // Navigate to the Personal Info screen
        System.out.println("Navigating to Personal Info...");
        // Implement your logic for showing Personal Info
    }

    private static void showNewBookingScene() {
        // Navigate to the New Booking screen
        System.out.println("Navigating to New Booking...");
        // Implement your logic for showing New Booking
    }

    private void showPaymentScene() {
        // Tạo một cửa sổ mới (Stage) để hiển thị màn hình Payment
        Stage stage = new Stage();

        // Lấy giao diện từ class Payment
        VBox paymentScreen = new Payment(this.student_id).createPaymentScreen(); // Lấy màn hình Payment từ class Payment

        // Tạo một Scene mới với giao diện Payment
        Scene paymentScene = new Scene(paymentScreen, 600, 400); // Thay đổi kích thước nếu cần

        // Đặt Scene vào Stage và hiển thị Stage
        stage.setTitle("Payment Screen");
        stage.setScene(paymentScene);
        stage.show();
    }

}
