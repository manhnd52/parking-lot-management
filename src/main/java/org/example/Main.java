package org.example;

import Service.ParkingLotService;
import Service.ReturnResult.PaymentMethod;
import Service.StaffService;
import Service.StudentService;
import dao.*;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Student student1 = new Student("20225880", "Nguyen Duc Manh", "123456", 100000, "nva@example.com");
        Pending pending = new Pending(student1,10);
        PendingDAO pendingDAO = new PendingDAO();
        pendingDAO.insert(pending);




    }
}
