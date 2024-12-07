package Group13.parking_lot_management.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Pending {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	@JoinColumn(name="student_id", nullable = false)
	private Student student;
	private int amount;
	private Timestamp created_at;
	
	
	public Pending() {
	}
	
	public Pending(Student student, int amount, Timestamp createdAt) {
		super();
		this.student = student;
		this.amount = amount;
		this.created_at = createdAt;
	}


	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getId() {
		return id;
	}
	public Timestamp getCreatedAt() {
		return created_at;
	}
	
	
}
