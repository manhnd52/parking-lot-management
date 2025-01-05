package model;

import jakarta.persistence.*;


import java.sql.*;
import java.util.ArrayList;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Transaction {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    private int amount;
    private Timestamp created_at;


    public Transaction() {
        super();
    }

    public Transaction(Student student, int amount) {
        this.student = student;
        this.amount = amount;
        this.created_at = new Timestamp(System.currentTimeMillis());
    }
    public int getId() {
        return id;
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
    public Timestamp getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }


}