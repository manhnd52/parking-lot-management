package Group13.parking_lot_management;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import Group13.parking_lot_management.dao.StudentDAO;
import Group13.parking_lot_management.model.ParkingLot;
import Group13.parking_lot_management.model.Staff;
import Group13.parking_lot_management.model.Student;

public class App 
{
    public static void main( String[] args )
    {
    	//initData.doIt();
    	StudentDAO run = new StudentDAO();
    	Student std = run.getByKey(20225880);
    	System.out.println(std);
    	std.setBalance(std.getBalance() + 20000);
    	run.saveOrUpdate(std);
    	std = run.getByKey(20225880);
    	System.out.println(std);
    }
}