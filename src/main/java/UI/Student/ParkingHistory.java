package UI.Student;

import Service.StudentService;
import dao.ParkingHistoryDAO;
import dao.ParkingLotDAO;
import dao.StudentDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.*;
import model.Student;

public class ParkingHistory {
    private String student_id;
    private StudentService studentService=new StudentService();
    private ObservableList<model.ParkingHistory>parkingHistories= FXCollections.observableArrayList(new ParkingHistoryDAO().getByStudentId(this.student_id));


    public ParkingHistory(String student_id) {
        this.student_id = student_id;
        parkingHistories= FXCollections.observableArrayList(new ParkingHistoryDAO().getByStudentId(this.student_id));
    }

    public ParkingHistory(){}

    public VBox createParkingHistoryScreen() {
        VBox mainContainer = new VBox(15);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setStyle("-fx-background-color: white;");

        // Header section
        Label headerLabel = new Label("Lịch sử đỗ xe");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 18));

        // History table
        TableView<model.ParkingHistory> historyTable = createHistoryTable();
        VBox.setVgrow(historyTable, Priority.ALWAYS);

        mainContainer.getChildren().addAll(headerLabel, historyTable);
        return mainContainer;
    }

    private static void addInfoRow(GridPane grid, int row, String label, String value) {
        Label lblTitle = new Label(label);
        Label lblValue = new Label(value);
        lblTitle.setFont(Font.font("System", FontWeight.BOLD, 12));
        grid.addRow(row, lblTitle, lblValue);
    }

    private TableView<model.ParkingHistory> createHistoryTable() {
        TableView<model.ParkingHistory> table = new TableView<>();
        TableColumn<model.ParkingHistory, String> vehicleCol = new TableColumn<>("Loại xe");
        vehicleCol.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getVehicle_type().getName()));
        TableColumn<model.ParkingHistory, String> timeInCol = new TableColumn<>("TG vào");
        timeInCol.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getEntry_time().toString()));
        TableColumn<model.ParkingHistory, String> timeOutCol = new TableColumn<>("TG ra");
        timeOutCol.setCellValueFactory(cellData -> {
            String exitTimeStr = (cellData.getValue().getExit_time() != null)
                    ? cellData.getValue().getExit_time().toString()
                    : "Chưa ra";
            return new SimpleStringProperty(exitTimeStr);
        });
        TableColumn<model.ParkingHistory, String> areaCol = new TableColumn<>("Khu vực");
        areaCol.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getParking_lot().getName()));
        TableColumn<model.ParkingHistory,Integer> statusCol = new TableColumn<>("Thanh toán");
        statusCol.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getFee()).asObject());
        table.setItems(parkingHistories);
        table.getColumns().addAll(vehicleCol,timeInCol, timeOutCol, areaCol, statusCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }
}
