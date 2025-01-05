package UI.Staff.MainMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Timestamp;
import java.util.Calendar;


public class TicketController {
    private int id;
    private String xe;
    private String location;

    public TicketController(){}

    public TicketController(int id,String xe,String location){
        this.id = id;
        this.xe = xe;
        this.location = location;
    }

    @FXML
    private Label timeLB;
    @FXML
    private Label locationLB;
    @FXML
    private Label idLB;
    @FXML
    private Label vehicleLB;
    @FXML
    private Label tbLB;

    @FXML
    void btnpressed(ActionEvent event){
        tbLB.setText("IN VÉ THÀNH CÔNG");
    }

    @FXML
    void initialize(){
        timeLB.setText(new Timestamp(Calendar.getInstance().getTime().getTime()).toString());
        locationLB.setText(this.location);
        idLB.setText(String.valueOf(this.id));
        vehicleLB.setText(this.xe);
    }

}
