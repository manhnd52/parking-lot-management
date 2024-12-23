package Group13.parking_lot_management.Service;

import java.sql.Timestamp;
import java.util.Calendar;

import Group13.parking_lot_management.Service.ReturnResult.CheckInResult;
import Group13.parking_lot_management.Service.ReturnResult.StudentOutResult;
import Group13.parking_lot_management.Service.ReturnResult.VisitorInResult;
import Group13.parking_lot_management.Service.ReturnResult.VisitorOutResult;
import Group13.parking_lot_management.dao.ParkingHistoryDAO;
import Group13.parking_lot_management.dao.ParkingLotDAO;
import Group13.parking_lot_management.dao.StudentDAO;
import Group13.parking_lot_management.dao.VehicleTypeDAO;
import Group13.parking_lot_management.model.ParkingHistory;
import Group13.parking_lot_management.model.ParkingLot;
import Group13.parking_lot_management.model.Student;
import Group13.parking_lot_management.model.VehicleType;

public class ParkingLotService {
	private ParkingLotDAO parkingLotDAO = new ParkingLotDAO();
	private ParkingHistoryDAO parkingHistoryDAO = new ParkingHistoryDAO();
	private StudentDAO studentDAO = new StudentDAO();
	private VehicleTypeDAO vehicleTypeDAO = new VehicleTypeDAO();

	/**
	 * Trả về số lượng xe đang trong bãi
	 */
	public int getCurrentCount(int parkingLotId) {
		ParkingLot parkingLot = parkingLotDAO.getByKey(parkingLotId);
		return parkingLot.getCurrent_count();
	}
	
	private int availableSlot(int id) {
		ParkingLot parkingLot = parkingLotDAO.getByKey(id);
		return (parkingLot.getCapacity() - parkingLot.getCurrent_count());
	}

	// Các trạng thái có thể có của Check In -> dùng cho các trường hợp khi làm giao diện
	
	
	/**
	 * Kiểm tra điều kiện lúc xe vào đối với sinh viên
	 */
	private CheckInResult checkIn(Student student, VehicleType vehicleType, int parkId) {
		StudentService studentService = new StudentService();
		if (availableSlot(parkId) < vehicleType.getSize()) {
	        return CheckInResult.NO_AVAILABLE_SLOT;
	    }
		
		// Kiểm tra mỗi sinh viên chỉ được gửi một xe
	    if (studentDAO.getCurrentPosition(student) != null) {
	        return CheckInResult.ALREADY_PARKING;
	    }

	    // Kiểm tra số dư tài khoản
	    if (!studentService.checkBalance(student, vehicleType)) {
	        return CheckInResult.INSUFFICIENT_BALANCE;
	    }

	    return CheckInResult.SUCCESS_IN;
	    
	}

	/**
	 * Ứng dụng tính đa hình -> dùng kiểm tra điều kiện đầu vào cho Khách vãng lai
	 */
	private CheckInResult checkIn(VehicleType vehicleType, int parkId) {
		return availableSlot(parkId) >= vehicleType.getSize() 
				 ? CheckInResult.SUCCESS_IN 
				 : CheckInResult.NO_AVAILABLE_SLOT;
	}

	/**
	 * Cho xe của STUDENT vào bãi
	 * 
	 * @return Thêm vào thành công hay chưa? -> dùng xử lý hiện thông báo trong giao
	 *         diện
	 */
	public CheckInResult studentIn(int parkId, String studentId, String vehicleTypeName, String license_plate) {
		VehicleType vehicleType = vehicleTypeDAO.getByName(vehicleTypeName);
		ParkingLot parkingLot = parkingLotDAO.getByKey(parkId);
		Student student = studentDAO.getByKey(studentId);
		CheckInResult result = checkIn(student, vehicleType, parkId);
        if (result != CheckInResult.SUCCESS_IN) {
            return result; 
        }
        
		parkingLot.setCurrent_count(parkingLot.getCurrent_count() + vehicleType.getSize());
		parkingLotDAO.saveOrUpdate(parkingLot);
		
		ParkingHistory parkingHistory = new ParkingHistory(parkingLot, student, vehicleType, license_plate);
		parkingHistoryDAO.insert(parkingHistory);
		return CheckInResult.SUCCESS_IN;
	}

	/**
	 * Cho xe của KHÁCH VÃNG LAI vào bãi
	 * 
	 * @return Vé xe cho khách vãng lai (0: là không là vào bãi không thành công)
	 */
	public VisitorInResult visitorIn(int parkId, String vehicleTypeName, String license_plate) {
		ParkingLot parkingLot = parkingLotDAO.getByKey(parkId);
		VehicleType vehicleType = vehicleTypeDAO.getByName(vehicleTypeName);
		CheckInResult result = checkIn(vehicleType, parkId);
		
        if (result != CheckInResult.SUCCESS_IN) {
            return new VisitorInResult(result, 0); 
        }
        
		parkingLot.setCurrent_count(parkingLot.getCurrent_count() + vehicleType.getSize());
		parkingLotDAO.saveOrUpdate(parkingLot);
		
		ParkingHistory parkingHistory = new ParkingHistory(parkingLot, null, vehicleType, license_plate);
		parkingHistoryDAO.insert(parkingHistory);
		
		int ticket = parkingHistory.getId();
		return new VisitorInResult(result, ticket);
	}

	public int getFee(VehicleType vehicleType, Timestamp current_time) {
		@SuppressWarnings("deprecation")
		boolean isNightTime = current_time.getHours() > 18;
		int applicableFee = isNightTime ? vehicleType.getNight_fee() : vehicleType.getSession_fee();
		return applicableFee;
	}

	/**
	 * Cho sinh viên ra {@return} true/false: thành công/ không tìm thấy sinh viên
	 * trong bãi
	 */
	
	public StudentOutResult studentOut(String student_id) {
		ParkingHistory parking = parkingHistoryDAO.getCurrentParking(student_id);
		if (parking != null) {
			Timestamp current_time = new Timestamp(Calendar.getInstance().getTime().getTime());
			parking.setExit_time(current_time);

			// Tính phí
			int applicableFee = getFee(parking.getVehicle_type(), current_time);
			parking.setFee(applicableFee);
			parkingHistoryDAO.saveOrUpdate(parking);

			// Cập nhật số vị trí trong bãi đỗ xe
			ParkingLot parkingLot = parking.getParking_lot();
			parkingLot.setCurrent_count(parkingLot.getCurrent_count() - parking.getVehicle_type().getSize());
			parkingLotDAO.saveOrUpdate(parkingLot);

			// Trừ tiền số dư sinh viên
			Student student = parking.getStudent();
			StudentService studentService = new StudentService();
			studentService.reduceBalance(student, applicableFee);
			return StudentOutResult.SUCCESS_OUT;
		}
		return StudentOutResult.NOT_PARKING;
	}

	/**
	 * Khách vãng lai ra bãi
	 * 
	 * @return phí của khách vãng lai (0 là không tìm thấy mã vẽ)
	 */
	public VisitorOutResult visitorOut(int ticketId) {
		ParkingHistory parking = parkingHistoryDAO.getByKey(ticketId);
		if (parking == null) {
			return VisitorOutResult.NOT_FOUND;
		}
		
		if (parking.getExit_time() != null) {
			return VisitorOutResult.USED_TICKET;
		}
			
		Timestamp current_time = new Timestamp(Calendar.getInstance().getTime().getTime());
		parking.setExit_time(current_time);

		// Tính phí
		int applicableFee = getFee(parking.getVehicle_type(), current_time);
		parking.setFee(applicableFee);
		parkingHistoryDAO.saveOrUpdate(parking);

		// Cập nhật số vị trí trong bãi đỗ xe
		ParkingLot parkingLot = parking.getParking_lot();
		parkingLot.setCurrent_count(parkingLot.getCurrent_count() - parking.getVehicle_type().getSize());
		parkingLotDAO.saveOrUpdate(parkingLot);

		return VisitorOutResult.SUCCESS_OUT;
	}


}
