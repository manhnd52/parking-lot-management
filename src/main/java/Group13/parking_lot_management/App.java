package Group13.parking_lot_management;

import Group13.parking_lot_management.Service.*;
public class App {
    public static void main(String[] args) {
    	initData.doIt();
        // DAO objects
        // Service objects
        ParkingLotService parkingLotService = new ParkingLotService();
        StaffService staffService = new StaffService();
        StudentService studentService = new StudentService();
       
//        System.out.println(parkingLotService.visitorIn(2, "Bike", null).getTicket());
        System.out.println(parkingLotService.visitorOut(263));
        System.out.println(staffService.changePassword(111, "123a456"));
        
    }
}