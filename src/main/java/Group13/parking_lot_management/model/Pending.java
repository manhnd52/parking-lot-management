package Group13.parking_lot_management.model;

import javax.persistence.Entity;

@Entity
public class Pending extends Transaction {

	public Pending() {
		super();
	}

	public Pending(Student student, int amount) {
		super(student, amount);
	}
}