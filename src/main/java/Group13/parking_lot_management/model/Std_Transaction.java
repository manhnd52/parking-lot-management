package model;

import java.sql.Timestamp;

import jakarta.persistence.*;
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

    @Override
    public String toString() {
        return "Std_Transaction [id=" + id + ", student=" + student + ", amount=" + amount + ", type=" + type
                + ", created_at=" + created_at + "]";
    }


}