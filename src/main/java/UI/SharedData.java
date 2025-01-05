package UI;

import dao.ParkingHistoryDAO;
import dao.ParkingLotDAO;
import dao.PendingDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ParkingHistory;
import model.ParkingLot;
import model.Pending;

public class SharedData {
    private static ObservableList<ParkingLot> parkingLots = FXCollections.observableArrayList(new ParkingLotDAO().selectAll());
    private static ObservableList<ParkingHistory>parkingHistories=FXCollections.observableArrayList(new ParkingHistoryDAO().selectAll());
    private static final ObservableList<Pending> pendings=FXCollections.observableArrayList(new PendingDAO().selectAll());

    public static ObservableList<ParkingLot> getParkingLots() {
        return parkingLots;
    }
    public static ObservableList<ParkingHistory> getParkingHistories() {
        return parkingHistories;
    }
    public static ObservableList<Pending> getPendings() {
        return pendings;
    }
}
