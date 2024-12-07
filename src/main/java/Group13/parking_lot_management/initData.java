package Group13.parking_lot_management;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import Group13.parking_lot_management.model.ParkingHistory;
import Group13.parking_lot_management.model.ParkingLot;
import Group13.parking_lot_management.model.Pending;
import Group13.parking_lot_management.model.Staff;
import Group13.parking_lot_management.model.Std_Transaction;
import Group13.parking_lot_management.model.Student;
import Group13.parking_lot_management.model.VehicleType;


public class initData {
	public static void doIt() {
		try {
			SessionFactory sessionFactory  = Group13.parking_lot_management.dao.HibernateUtil.getSessionFactory();
			if (sessionFactory != null) {
				Session session = sessionFactory.openSession();
				try {
					Transaction tr = session.beginTransaction();
			        VehicleType car = new VehicleType("Car", 5000, 20000, 15000, 3);
			        VehicleType bike = new VehicleType("Bike", 2000, 5000, 5000, 1);
			        VehicleType motorbike = new VehicleType("Motorbike", 3000, 5000, 5000, 1);
			        session.save(car);
			        session.save(bike);
			        session.save(motorbike);

			        ParkingLot lotA = new ParkingLot("D9", 50, 0);
			        ParkingLot lotB = new ParkingLot("C7", 100, 0);
			        ParkingLot lotC = new ParkingLot("A2", 80, 0);
			        ParkingLot lotD = new ParkingLot("B5", 120, 0);
			        session.save(lotA);
			        session.save(lotB);
			        session.save(lotC);
			        session.save(lotD);
			        
			        Student student1 = new Student("20225880", "Nguyen Duc Manh", "123456", 100000, "nva@example.com");
			        Student student2 = new Student("20225881", "Le Thi Hau", "password456", 50000, "ltb@example.com");
			        Student student3 = new Student("20225882", "Pham Van Binh", "password123", 80000, "binh@example.com");
			        Student student4 = new Student("20225883", "Nguyen Thi Lan", "password456", 120000, "lan@example.com");
			        Student student5 = new Student("20225884", "Tran Van Son", "password789", 70000, "son@example.com");
			        Student student6 = new Student("20225885", "Le Van Tam", "password101", 50000, "tam@example.com");
			        Student student7 = new Student("20225886", "Hoang Thi Hoa", "password202", 90000, "hoa@example.com");
			        Student student8 = new Student("20225887", "Vu Van Khang", "password303", 40000, "khang@example.com");
			        Student student9 = new Student("20225888", "Nguyen Minh Tri", "password404", 100000, "tri@example.com");
			        Student student10 = new Student("20225889", "Dang Thi Lien", "password505", 60000, "lien@example.com");
			        Student student11 = new Student("20225890", "Nguyen Van Ha", "password606", 110000, "ha@example.com");
			        Student student12 = new Student("20225891", "Phan Thi Cuc", "password707", 55000, "cuc@example.com");
			        session.save(student1);
			        session.save(student2);
			        session.save(student3);
			        session.save(student4);
			        session.save(student5);
			        session.save(student6);
			        session.save(student7);
			        session.save(student8);
			        session.save(student9);
			        session.save(student10);
			        session.save(student11);
			        session.save(student12);
			        List<Student> Students = new ArrayList<Student>();
			        Students.add(student1);
			        Students.add(student2);
			        Students.add(student3);
			        Students.add(student4);
			        Students.add(student5);
			        Students.add(student6);
			        Students.add(student7);
			        Students.add(student8);
			        Students.add(student9);
			        Students.add(student10);
			        Students.add(student11);
			        Students.add(student12);
			        
			        Std_Transaction txn1 = new Std_Transaction(student1, 20000, true, new Timestamp(System.currentTimeMillis()));
			        Std_Transaction txn2 = new Std_Transaction(student2, 15000, true, new Timestamp(System.currentTimeMillis()));
			        session.save(txn1);
			        session.save(txn2);

			        Pending pending1 = new Pending(student1, 10000, new Timestamp(System.currentTimeMillis()));
			        session.save(pending1);

			        ParkingHistory history1 = new ParkingHistory(lotA, student1, car, "29A-12345",
			            Timestamp.valueOf("2024-12-07 08:00:00"), Timestamp.valueOf("2024-12-07 12:00:00"), 20000);
			        ParkingHistory history2 = new ParkingHistory(lotB, null, bike, "59C-67890",
			            Timestamp.valueOf("2024-12-07 09:00:00"), Timestamp.valueOf("2024-12-07 11:00:00"), 10000);
			        session.save(history1);
			        session.save(history2);
			        for (int i = 1; i <= 100; i++) {
			            Student assignedStudent = (i % 5 == 0) ? null : Students.get((new Random()).nextInt(11) + 1);
			            ParkingLot assignedLot = (i % 4 == 0) ? lotD : (i % 3 == 0 ? lotC : (i % 2 == 0 ? lotB : lotA));
			            VehicleType assignedVehicleType = (i % 5 == 0) ? car : ((i % 15 == 1 ) ? bike : motorbike);

			            ParkingHistory parkingHistory = new ParkingHistory(
			                assignedLot,
			                assignedStudent,
			                assignedVehicleType,
			                i % 15 != 0 ? "29A-" + (1000 + i) : null,
			                Timestamp.valueOf("2024-12-07 08:00:00"),
			                Timestamp.valueOf("2024-12-07 10:00:00"),
			                (assignedVehicleType == car) ? 15000 : 5000
			            );
			            session.save(parkingHistory);
			        }

			        Staff staff1 = new Staff("Tran Van Duc", "123456", lotA);
			        Staff staff2 = new Staff("Pham Thi Dung", "123456", lotB);
			        Staff staff3 = new Staff("Tran Van Minh", "123456", lotC);
			        Staff staff4 = new Staff("Le Thi Dao", "123456", lotD);
			        session.save(staff1);
			        session.save(staff2);
			        session.save(staff3);
			        session.save(staff4);
			        tr.commit();
				} catch(Exception e) {
					System.out.println(e);				
				} finally {
					session.close();
					System.out.print("***SUCCESS INIT DATA***");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
