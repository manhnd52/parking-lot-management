package Group13.parking_lot_management.Service;

import java.util.ArrayList;
import java.util.List;

import Group13.parking_lot_management.Service.ReturnResult.TransactionDTO;
import Group13.parking_lot_management.dao.ParkingHistoryDAO;
import Group13.parking_lot_management.dao.PendingDAO;
import Group13.parking_lot_management.dao.Std_TransactionDAO;
import Group13.parking_lot_management.dao.StudentDAO;
import Group13.parking_lot_management.model.ParkingHistory;
import Group13.parking_lot_management.model.ParkingLot;
import Group13.parking_lot_management.model.Pending;
import Group13.parking_lot_management.model.Std_Transaction;
import Group13.parking_lot_management.model.Student;
import Group13.parking_lot_management.model.Transaction;
import Group13.parking_lot_management.model.VehicleType;

public class StudentService {
	private StudentDAO studentDAO = new StudentDAO();
	private Std_TransactionDAO std_TransactionDAO = new Std_TransactionDAO();
	private PendingDAO pendingDAO = new PendingDAO();
	private ParkingHistoryDAO parkingHistoryDAO = new ParkingHistoryDAO();
	
	public boolean checkLogin(String username, String password) {
		Student student = studentDAO.getByKey(username);
		if (student != null) {
			return student.getPassword().equals(password);
		}
		return false;
	}

	public String getName(String studentId) {
		Student student = studentDAO.getByKey(studentId);
		return student.getFullname();
	}
	
	public boolean changePassword(String studentId, String password) {
		Student student = studentDAO.getByKey(studentId);
		student.setPassword(password);
		if (studentDAO.saveOrUpdate(student))
			return true;
		else
			return false;
	}
	
	boolean checkBalance(Student student, VehicleType vehicleType) {
		return student.getBalance() >= vehicleType.getSession_fee();
	}
	
	public int getBalance(String studentId) {
		Student student = studentDAO.getByKey(studentId);
		return student.getBalance();
	}
	
	public String getCurrentPosition(String studentId) {
		ParkingLot currentParkingLot = studentDAO.getCurrentPosition(studentId);
		return currentParkingLot.getName();
	}
	
	/**
	 * Phương thức Giảm số dư của Sinh viên Kết quả trả về là TRẠNG THÁI CỦA GIAO DỊCH
	 */
	public boolean reduceBalance(Student student, int fee) {
		int currentBalance = student.getBalance();
		if (fee <= currentBalance) {
			student.setBalance(currentBalance - fee);
			studentDAO.saveOrUpdate(student);
			return true;
		}
		return false;
	}
	
	/**
	 * Phương thức Tăng tiền của Sinh viên 
	 * @return TRẠNG THÁI CỦA GIAO DỊCH
	 * Ví dụ của default (phân quyền)
	 */
	
	boolean depositBalance(String studentId, int amount) {
		if (amount < 0) return false;
		Student student = studentDAO.getByKey(studentId);
		int currentBalance = student.getBalance();
		student.setBalance(currentBalance + amount);
		if (studentDAO.saveOrUpdate(student)) {
			return true;
		}
		return false;
	}
	
	public void requestTransaction(String studentId, int amount) {
		Student student = studentDAO.getByKey(studentId);
		Pending pending = new Pending(student, amount);
		pendingDAO.saveOrUpdate(pending);
	}

	public List<TransactionDTO> getListTransaction(String studentId) {
		List<TransactionDTO> transactions = new ArrayList<>();
		List<Std_Transaction> didTransaction = std_TransactionDAO.getByStudentId(studentId);
		List<Pending> pendingTransaction = pendingDAO.getByStudentId(studentId);
		List<ParkingHistory> parkingTransaction = parkingHistoryDAO.getByStudentId(studentId);
		for (Transaction t : didTransaction) {
			transactions.add(new TransactionDTO(
					t.getCreated_at(), 
					t.getAmount(), 
					"DEPOSIT"));
		}
		for (Transaction t : pendingTransaction) {
			transactions.add(new TransactionDTO(
					t.getCreated_at(), 
					t.getAmount(), 
					"PENDING"));
		}
		for (ParkingHistory t : parkingTransaction) {
			transactions.add(new TransactionDTO(
					t.getExit_time(), 
					t.getFee(), 
					"PARK"));
		}
		return transactions;
		
	}
}
