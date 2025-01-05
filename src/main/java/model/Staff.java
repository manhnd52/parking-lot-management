package model;


import jakarta.persistence.*;

@Entity
public class Staff {
    @Id
    @GeneratedValue
    private int id;
    private String fullname;
    private String password;

    @ManyToOne
    @JoinColumn(name = "parkinglot_id", nullable = false)
    private ParkingLot parkingLot;

    public Staff() {
        super();
    }

    public Staff(String fullname, String password, ParkingLot parkingLot) {
        super();
        this.fullname = fullname;
        this.password = password;
        this.parkingLot=parkingLot;
    }

    public int getId() {
        return id;
    }

    public void setId(int iD) {
        id= iD;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

}
