package UI.Student;

import Service.StudentService;
import UI.SharedData;
import dao.PendingDAO;
import dao.Std_TransactionDAO;
import dao.StudentDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Pending;
import model.Std_Transaction;

import java.awt.*;

public class Payment {
    private String student_id;
    private StudentService studentService = new StudentService();
    private ObservableList<Std_Transaction> transactions= FXCollections.observableArrayList(new Std_TransactionDAO().getByStudentId(student_id));
    //private Label balance=new Label();


    public Payment(String student_id) {
        this.student_id = student_id;
        transactions = FXCollections.observableArrayList(new Std_TransactionDAO().getByStudentId(student_id));
    }
    public Payment(){}

    public VBox createPaymentScreen() {
        VBox mainContainer = new VBox(15);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setStyle("-fx-background-color: white;");

        Label titleLabel = new Label("Thanh toán");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 20));

        TextField amountField = new TextField();
        amountField.setPromptText("Nhập số dư mới");
        // Current parking details
        VBox parkingDetails = createParkingDetails();

        // Account balance
        VBox accountBalance = createAccountBalance(amountField);

        // Payment buttons
        HBox paymentButtons = createPaymentButtons(amountField);

        mainContainer.getChildren().addAll(titleLabel, parkingDetails, accountBalance, paymentButtons);
        return mainContainer;
    }

    private VBox createParkingDetails() {
        VBox details = new VBox(10);
        details.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 15;");


        GridPane info = new GridPane();
        info.setHgap(15);
        info.setVgap(10);

        addInfoRow(info, 0, "Sinh viên:",new StudentDAO().getByKey(this.student_id).getFullname());
        addInfoRow(info, 1, "MSSV:",this.student_id);
        addInfoRow(info, 2, "Email: ",new StudentDAO().getByKey(this.student_id).getEmail());
//        addInfoRow(info, 3, "Vị trí đỗ xe:", "28");
//        addInfoRow(info, 4, "Giá tiền:", "5000 VND");

        details.getChildren().addAll(info);
        return details;
    }

    private VBox createAccountBalance(TextField amountField) {
        VBox balance = new VBox(10);
        balance.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 15;");

        Label sectionTitle = new Label("Kiểm tra số dư trong tài khoản");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 14));

        Label sodu = new Label("Số dư: ");
        sodu.setFont(Font.font("System", 14));

        Label balanceAmount = new Label(new StudentDAO().getByKey(this.student_id).getBalance()+"");
        balanceAmount.setFont(Font.font("System", FontWeight.BOLD, 14));
        //this.balance=balanceAmount;

        HBox hbox1 = new HBox(10); // khoảng cách giữa các thành phần là 10px
        hbox1.getChildren().addAll(sodu,balanceAmount);
        hbox1.setStyle("-fx-padding: 10;");


        Button updateButton = new Button("Cập nhật số dư mới");
        updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        updateButton.setOnAction(e -> {
            balanceAmount.setText(new StudentDAO().getByKey(this.student_id).getBalance()+"");

        });

        // Sử dụng HBox để đặt các thành phần theo chiều ngang
        HBox hbox = new HBox(10); // khoảng cách giữa các thành phần là 10px
        hbox.getChildren().addAll(updateButton,amountField);
        hbox.setStyle("-fx-padding: 10;");

        balance.getChildren().addAll(sectionTitle,hbox1,hbox);
        return balance;
    }

    public HBox createPaymentButtons(TextField amountField) {
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        //Button backButton = new Button("Quay lại");
        Button payButton = new Button("Thanh toán");
        payButton.setOnAction(actionEvent -> {
            studentService.requestTransaction(this.student_id, Integer.parseInt(amountField.getText()));

            // Lấy danh sách mới từ cơ sở dữ liệu
            ObservableList<Pending> updatedPendings = FXCollections.observableArrayList(new PendingDAO().selectAll());

            // Xóa toàn bộ và thêm mới vào danh sách SharedData.getPendings()
            SharedData.getPendings().clear();
            SharedData.getPendings().addAll(updatedPendings);
        });

        Button historyButton = new Button("Lịch sử giao dịch");
        historyButton.setOnAction(actionEvent -> {
            Stage stage = new Stage();

            // Lấy giao diện từ class ParkingHistory
            VBox createTransactionScreen = new Payment(this.student_id).createTransactionScreen(); // Lấy màn hình Parking History từ class ParkingHistory

            // Tạo một Scene mới với giao diện Parking History
            Scene parkingHistoryScene = new Scene(createTransactionScreen, 600, 400); // Thay đổi kích thước nếu cần

            // Đặt Scene vào Stage và hiển thị Stage
            stage.setTitle("Transaction History");
            stage.setScene(parkingHistoryScene);
            stage.show();
        });
        payButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        historyButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        buttons.getChildren().addAll(payButton, historyButton);
        return buttons;
    }

    private static void addInfoRow(GridPane grid, int row, String label, String value) {
        Label lblTitle = new Label(label);
        Label lblValue = new Label(value);
        lblTitle.setFont(Font.font("System", FontWeight.BOLD, 12));
        grid.addRow(row, lblTitle, lblValue);
    }

    public VBox createTransactionScreen() {
        VBox mainContainer = new VBox(15);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setStyle("-fx-background-color: white;");

        // Header section
        Label headerLabel = new Label("Lịch sử giao dịch");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 18));

        // History table
        TableView<Std_Transaction> historyTable = createHistoryTable();
        VBox.setVgrow(historyTable, Priority.ALWAYS);

        mainContainer.getChildren().addAll(headerLabel, historyTable);
        return mainContainer;
    }

    private TableView<Std_Transaction> createHistoryTable() {
        TableView<Std_Transaction> table = new TableView<>();
        TableColumn<Std_Transaction, Integer> idcol = new TableColumn<>("ID");
        idcol.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        TableColumn<Std_Transaction, Integer> amountcol = new TableColumn<>("Amount");
        amountcol.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getAmount()).asObject());
        TableColumn<Std_Transaction, String> createatcol = new TableColumn<>("Created_at");
        createatcol.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getCreated_at().toString()));
        TableColumn<Std_Transaction, String> methodcol = new TableColumn<>("Payment_method");
        methodcol.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getPaymentMethod().toString()));
        table.setItems(transactions);
        table.getColumns().addAll(idcol, amountcol, createatcol, methodcol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }
}
