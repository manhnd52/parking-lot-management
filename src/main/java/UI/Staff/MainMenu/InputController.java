package UI.Staff.MainMenu;

import Service.ParkingLotService;
import UI.SharedData;
import dao.ParkingHistoryDAO;
import dao.ParkingLotDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ParkingHistory;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;


public class InputController {
    private ParkingLotService parkingLotService=new ParkingLotService();
    private int parkinglotID;
    private int khachID;

    public InputController(int id) {
        this.parkinglotID=id;
    }

    @FXML
    private Label parkinglotLB;
    @FXML
    private Label dateLB;
    @FXML
    private TextField mssvTXT;
    @FXML
    private TextField xeSVTXT;
    @FXML
    private TextField plateSVTXT;

    @FXML
    private TextField idTXT;
    @FXML
    private TextField xeTXT;
    @FXML
    private TextField plateTXT;

    @FXML
    void themBTN(ActionEvent event) {
        parkingLotService.studentIn(this.parkinglotID,mssvTXT.getText(),xeSVTXT.getText(),plateSVTXT.getText());
        SharedData.getParkingLots().setAll(new ParkingLotDAO().selectAll());
        SharedData.getParkingHistories() .setAll(new ParkingHistoryDAO().selectAll());
    }

    @FXML
    void phatveBTN(ActionEvent event) {
        parkingLotService.visitorIn(parkinglotID,xeTXT.getText(),plateTXT.getText());
        SharedData.getParkingLots().setAll(new ParkingLotDAO().selectAll());
        SharedData.getParkingHistories() .setAll(new ParkingHistoryDAO().selectAll());
        TicketController ticketController=new TicketController(Integer.parseInt(idTXT.getText()),xeTXT.getText(),new ParkingLotDAO().getByKey(parkinglotID).getName());
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("ticket.fxml"));
        fxmlLoader.setController(ticketController);
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage newStage = new Stage();
        Scene scene = new Scene(root);
        newStage.setTitle("Ticket");
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    void choraBTN(ActionEvent event) {
        parkingLotService.studentOut(mssvTXT.getText(),this.parkinglotID);
        SharedData.getParkingLots().setAll(new ParkingLotDAO().selectAll());
        SharedData.getParkingHistories().setAll(new ParkingHistoryDAO().selectAll());
    }

    @FXML
    void chorakhachBTN(ActionEvent event) {
        parkingLotService.visitorOut(Integer.parseInt(idTXT.getText()),this.parkinglotID);
        SharedData.getParkingLots().setAll(new ParkingLotDAO().selectAll());
        SharedData.getParkingHistories().setAll(new ParkingHistoryDAO().selectAll());
    }

    @FXML
    void initialize() {
        parkinglotLB.setText("Khu vực: "+new ParkingLotDAO().getByKey(parkinglotID).getName());
        dateLB.setText(new Timestamp(Calendar.getInstance().getTime().getTime()).toString());

    }






}
