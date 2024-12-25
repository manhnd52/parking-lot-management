package Group13.parking_lot_management.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import Group13.parking_lot_management.Service.ReturnResult.PaymentMethod;

// Thông tin lịch sử nạp tiền của sinh viên;
@Entity
public class Std_Transaction extends Transaction{

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method", nullable = false)
	private PaymentMethod paymentMethod;

	public Std_Transaction() {
	}

	public Std_Transaction(Student student, int amount, PaymentMethod paymentMethod) {
		super(student, amount);
		this.paymentMethod = paymentMethod;
	}
	
	
	public Std_Transaction(Student student, int amount, Timestamp createdAt, PaymentMethod paymentMethod) {
		super(student, amount);
		this.setCreated_at(createdAt);
		this.paymentMethod = paymentMethod;

	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	@Override
	public String toString() {
		return "Std_Transaction [id=" + getId() + ", student=" + getStudent() + ", amount=" + getAmount() + ", method=" + paymentMethod
				+ ", created_at=" + getCreated_at() + "]";
	}

}