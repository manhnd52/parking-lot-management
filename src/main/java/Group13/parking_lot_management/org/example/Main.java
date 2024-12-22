package org.example;

import Service.ParkingLotService;
import Service.StaffService;
import Service.StudentService;
import dao.HibernateUtil;
import dao.ParkingHistoryDAO;
import dao.StudentDAO;
import dao.VehicleTypeDAO;
import model.*;
import org.hibernate.Session;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            StudentService studentService = new StudentService();
            for(Std_Transaction x:studentService.sortTransactionASC()){
                System.out.println(x);
            }

    }
}
