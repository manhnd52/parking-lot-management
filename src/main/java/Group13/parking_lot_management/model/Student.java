package model;

import jakarta.persistence.*;

@Entity
public class Student {
    @Id
    private String student_id;
    private String password;
    private String fullname;
    private int balance;
    private String email;


    public Student() {
    }


    public Student(String student_id,String fullname, String password, int balance, String email) {
        super();
        this.student_id = student_id;
        this.fullname = fullname;
        this.password = password;
        this.balance = balance;
        this.email = email;
    }


    public String getStudent_id() {
        return student_id;
    }


    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getFullname() {
        return fullname;
    }


    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    public int getBalance() {
        return balance;
    }


    public void setBalance(int balance) {
        this.balance = balance;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "model.Student [student_id=" + student_id + ", fullname=" + fullname + ", email=" + email + ", balance="
                + balance + ", password=" + password + "]";
    }


}
