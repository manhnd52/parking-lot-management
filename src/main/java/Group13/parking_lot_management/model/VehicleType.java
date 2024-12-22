package model;

import jakarta.persistence.*;

@Entity
public class VehicleType {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    //Phi gui trong nay
    private int session_fee;
    // Phi luc ra bai la buoi dem
    private int night_fee;
    // Phi theo so ngay gui
    private int day_fee;
    // Chiem bao nhieu slot trong bai
    private int size;

    @Override
    public String toString() {
        return "VehicleType [id=" + id + ", name=" + name + ", session_fee=" + session_fee + ", night_fee=" + night_fee
                + ", day_fee=" + day_fee + ", size=" + size + "]";
    }


    public VehicleType() {
        super();
    }


    public VehicleType(String name, int session_fee, int night_fee, int day_fee, int size) {
        super();
        this.name = name;
        this.session_fee = session_fee;
        this.night_fee = night_fee;
        this.day_fee = day_fee;
        this.size = size;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSession_fee() {
        return session_fee;
    }

    public void setSession_fee(int session_fee) {
        this.session_fee = session_fee;
    }

    public int getNight_fee() {
        return night_fee;
    }

    public void setNight_fee(int night_fee) {
        this.night_fee = night_fee;
    }

    public int getDay_fee() {
        return day_fee;
    }

    public void setDay_fee(int day_fee) {
        this.day_fee = day_fee;
    }

    public int getId() {
        return id;
    }


    public int getSize() {
        return size;
    }


    public void setSize(int size) {
        this.size = size;
    }


}