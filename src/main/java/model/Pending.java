package model;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
public class Pending extends Transaction {
    public Pending() {
        super();
    }

    public Pending(Student student, int amount) {
        super(student, amount);
    }

}