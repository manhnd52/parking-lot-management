package Group13.parking_lot_management;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import Group13.parking_lot_management.dao.*;
import Group13.parking_lot_management.model.*;
public class App 
{
    public static void main( String[] args )
    {
    	//initData.doIt();
    	Session ss = HibernateUtil.getSessionFactory().openSession();
    	Transaction tr = ss.beginTransaction();
    	
    	
    	
    	StudentDAO dao = new StudentDAO();
    	ParkingLotDAO pDAO = new ParkingLotDAO();
    	VehicleType car = ss.get(VehicleType.class, 3);
    	Student std1 = dao.getByKey(20225880);    	
    	System.out.println(car);
    	ParkingLot plot = pDAO.getByKey(1);
    	
    	
    	ParkingHistoryDAO parkingHistoryDAO = new ParkingHistoryDAO();
//    	List l = dao.getParkingHistory("20225883");
//    	for (Object i : l) {
//    		System.out.println(i);
//    	}
//		ParkingHistory parkingHistory = new ParkingHistory(plot, std1, car, "1234", new Timestamp(System.currentTimeMillis()), null, 20000);
//		ss.save(parkingHistory);
		
		ParkingHistoryDAO dao2 = new ParkingHistoryDAO();
		System.out.println(dao2.getCurrentPosition(std1));
		
		System.out.println(dao.getCurrentPosition("20225880"));
		
		tr.commit();
		ss.close();
    }
}