package Group13.parking_lot_management.model;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity
public class ParkingHistory {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	@JoinColumn(name="parking_lot_id", nullable=false)
	private ParkingLot parking_lot;
	@ManyToOne
	@JoinColumn(name="student_id", nullable=true)
	private Student student;
	@ManyToOne
	@JoinColumn(name="vehicle_type_id", nullable=false)
	private VehicleType vehicle_type;
	private String license_plate;
	
	private Timestamp entry_time;
	private Timestamp exit_time;
	private int fee;
	
	public ParkingHistory() {}

	public ParkingHistory(ParkingLot parking_lot, Student student, 
			VehicleType vehicle_type, String license_plate, Timestamp entry_time, Timestamp exit_time, int fee) {
		super();
		this.parking_lot = parking_lot;
		this.student = student;
		this.vehicle_type = vehicle_type;
		this.license_plate = license_plate;
		this.entry_time = new Timestamp(System.currentTimeMillis());
		this.exit_time = exit_time;
		this.fee = fee;
	}

	public ParkingLot getParking_lot() {
		return parking_lot;
	}

	public void setParking_lot(ParkingLot parking_lot) {
		this.parking_lot = parking_lot;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public VehicleType getVehicle_type() {
		return vehicle_type;
	}

	public void setVehicle_type(VehicleType vehicle_type) {
		this.vehicle_type = vehicle_type;
	}

	public String getLicense_plate() {
		return license_plate;
	}

	public void setLicense_plate(String license_plate) {
		this.license_plate = license_plate;
	}
	
	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public int getId() {
		return id;
	}

	public Timestamp getEntry_time() {
		return entry_time;
	}

	public Timestamp getExit_time() {
		return exit_time;
	}

	@Override
	public String toString() {
		return "ParkingHistory [id=" + id + ", parking_lot=" + parking_lot.getName() + ", student=" + student.getStudent_id() + ", vehicle_type="
				+ vehicle_type.getName() + ", license_plate=" + license_plate + ", entry_time=" + entry_time + ", exit_time="
				+ exit_time + ", fee=" + fee + "]";
	}
	
	
}
