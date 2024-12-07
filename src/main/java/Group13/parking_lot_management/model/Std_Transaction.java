package Group13.parking_lot_management.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
// Thông tin lịch sử nạp tiền của sinh viên;
@Entity
public class Std_Transaction {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;
	private int amount;
	private Boolean type;
	private Timestamp created_at;
	
	public Std_Transaction() {}

	public Std_Transaction(Student student, int amount, Boolean type, Timestamp created_at) {
		this.student = student;
		this.amount = amount;
		this.type = type;
		this.created_at = created_at;
	}
	
	
	public int getId() {
		return id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Boolean getType() {
		return type;
	}

	public void setType(Boolean type) {
		this.type = type;
	}

	public Timestamp getCreatedAt() {
		return created_at;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
