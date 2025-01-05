package model;

import java.util.List;

import jakarta.persistence.*;

import org.hibernate.Session;

import dao.HibernateUtil;


@Entity
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int capacity;
    private int current_count;

    @OneToMany(mappedBy = "parkingLot", fetch = FetchType.EAGER)
    private List<Staff> listStaff;

    public ParkingLot() {}

    public ParkingLot(String name, int capacity, int current_count) {
        this.name = name;
        this.capacity = capacity;
        this.current_count = current_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrent_count() {
        return current_count;
    }

    public void setCurrent_count(int current_count) {
        this.current_count = current_count;
    }

    public List<Staff> getListStaff() {
        return listStaff;
    }

    public void setListStaff(List<Staff> listStaff) {
        this.listStaff = listStaff;
    }

    @Override
    public String toString() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return "ParkingLot [id=" + id + ", name=" + name + ", capacity=" + capacity + ", current_count=" + current_count
                    + ", numberStaff=" + listStaff.size() + "]";
        }
    }
}