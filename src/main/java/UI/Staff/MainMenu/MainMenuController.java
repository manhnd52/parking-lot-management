package UI.Staff.MainMenu;

import Service.StaffService;
import UI.SharedData;
import dao.PendingDAO;
import dao.StaffDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.ParkingHistory;
import model.ParkingLot;
import model.Pending;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;


public class MainMenuController {
    private StaffService staffService=new StaffService();
    private PendingDAO pendingDAO=new PendingDAO();

    private int staffID;

    public MainMenuController(int staffID){
        this.staffID=staffID;
    }

    @FXML
    private Label date;
    @FXML
    private Label nameStaff;
    @FXML
    private Label idStaff;

    @FXML
    public TableView<ParkingLot> tbParking;
    @FXML
    private TableColumn<ParkingLot, Integer> colID;
    @FXML
    private TableColumn<ParkingLot,String> colName;
    @FXML
    private TableColumn<ParkingLot, Integer> colCapacity;
    @FXML
    private TableColumn<ParkingLot, Integer> colCurrentCount;

    @FXML
    private TableView<Pending> tbPending;
    @FXML
    private TableColumn<Pending, Integer> colIDPending;
    @FXML
    private TableColumn<Pending,Integer> colAmountPending;
    @FXML
    private TableColumn<Pending, String> colCreatedPending;
    @FXML
    private TableColumn<Pending, String> colStudent;

    @FXML
    private TableView<ParkingHistory> tbParkingHistory;
    @FXML
    private TableColumn<ParkingHistory, Integer> colIDHistory;
    @FXML
    private TableColumn<ParkingHistory,String> colEntryTime;
    @FXML
    private TableColumn<ParkingHistory, String> colExitTime;
    @FXML
    private TableColumn<ParkingHistory, Integer> colFee;
    @FXML
    private TableColumn<ParkingHistory, String> colLicense;
    @FXML
    private TableColumn<ParkingHistory,Integer> colPK_ID;
    @FXML
    private TableColumn<ParkingHistory, String> colStudentID;
    @FXML
    private TableColumn<ParkingHistory,Integer> colVID;


    @FXML
    void btnThem(ActionEvent event) {
        InputController inputController=new InputController(tbParking.getSelectionModel().getSelectedItem().getId());
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("Input.fxml"));
        fxmlLoader.setController(inputController);
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage newStage = new Stage();
        Scene scene = new Scene(root);
        newStage.setTitle("Input");
        newStage.setScene(scene);
        newStage.show();
    }
    @FXML
    void btnDuyet(ActionEvent event) {
        Pending pending=tbPending.getSelectionModel().getSelectedItem();
        if(staffService.acceptPending(pending)) SharedData.getPendings().remove(pending);

    }
    @FXML
    void btnBo(ActionEvent event) {
        pendingDAO.delete(tbPending.getSelectionModel().getSelectedItem());
        SharedData.getPendings().remove(tbPending.getSelectionModel().getSelectedItem());
    }
    @FXML
    void btnLamMoi(ActionEvent event) {
        tbPending.setItems(null); // Bỏ liên kết cũ
        colIDPending.setCellValueFactory(cellData ->(new SimpleIntegerProperty(cellData.getValue().getId()).asObject()));
        colAmountPending.setCellValueFactory(cellData ->(new SimpleIntegerProperty(cellData.getValue().getAmount()).asObject()));
        colStudent.setCellValueFactory(cellData ->(new SimpleStringProperty(cellData.getValue().getStudent().getStudent_id())));
        colCreatedPending.setCellValueFactory(cellData->(new SimpleStringProperty(cellData.getValue().getCreated_at().toString())));
        tbPending.setItems(SharedData.getPendings()); // Gắn lại danh sách
        tbPending.refresh(); // Buộc làm mới bảng
    }



    @FXML
    void initialize() {
        date.setText(new Timestamp(Calendar.getInstance().getTime().getTime()).toString());
        nameStaff.setText(new StaffDAO().getByKey(this.staffID).getFullname());
        idStaff.setText(staffID+"");
        colID.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getId()).asObject()
        );
        colName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        colCapacity.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getCapacity()).asObject()
        );
        colCurrentCount.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getCurrent_count()).asObject()
        );
        tbParking.setItems(SharedData.getParkingLots());

        colIDPending.setCellValueFactory(cellData ->(new SimpleIntegerProperty(cellData.getValue().getId()).asObject()));
        colAmountPending.setCellValueFactory(cellData ->(new SimpleIntegerProperty(cellData.getValue().getAmount()).asObject()));
        colStudent.setCellValueFactory(cellData ->(new SimpleStringProperty(cellData.getValue().getStudent().getStudent_id())));
        colCreatedPending.setCellValueFactory(cellData->(new SimpleStringProperty(cellData.getValue().getCreated_at().toString())));
        tbPending.setItems(SharedData.getPendings());

        colIDHistory.setCellValueFactory(cellData ->(new SimpleIntegerProperty(cellData.getValue().getId()).asObject()));
        colEntryTime.setCellValueFactory(cellData ->(new SimpleStringProperty(cellData.getValue().getEntry_time().toString())));
        colExitTime.setCellValueFactory(cellData -> {
            String exitTimeStr = (cellData.getValue().getExit_time() != null)
                    ? cellData.getValue().getExit_time().toString()
                    : "Chưa ra";
            return new SimpleStringProperty(exitTimeStr);
        });
        colFee.setCellValueFactory(cellData ->(new SimpleIntegerProperty(cellData.getValue().getFee()).asObject()));
        colLicense.setCellValueFactory(cellData ->(new SimpleStringProperty(cellData.getValue().getLicense_plate())));
        colPK_ID.setCellValueFactory(cellData->(new SimpleIntegerProperty(cellData.getValue().getParking_lot().getId()).asObject()));
        colStudentID.setCellValueFactory(cellData -> {
            String student_id = (cellData.getValue().getStudent() != null)
                    ? cellData.getValue().getStudent().getStudent_id()
                    : "Khách vãng lai";
            return new SimpleStringProperty(student_id);
        });
        colVID.setCellValueFactory(cellData ->(new SimpleIntegerProperty(cellData.getValue().getVehicle_type().getId()).asObject()));
        tbParkingHistory.setItems(SharedData.getParkingHistories());



    }







}
