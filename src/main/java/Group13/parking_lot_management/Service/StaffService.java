package Group13.parking_lot_management.Service;

import java.util.List;

import Group13.parking_lot_management.Service.ReturnResult.PaymentMethod;
import Group13.parking_lot_management.dao.PendingDAO;
import Group13.parking_lot_management.dao.StaffDAO;
import Group13.parking_lot_management.dao.Std_TransactionDAO;
import Group13.parking_lot_management.model.Pending;
import Group13.parking_lot_management.model.Staff;
import Group13.parking_lot_management.model.Std_Transaction;

public class StaffService {
	private StaffDAO staffDAO = new StaffDAO();

	public boolean checkLogin(int username, String password) {
		Staff staff = staffDAO.getByKey(username);
		if (staff != null) {
			return staff.getPassword().equals(password);
		}
		return false;
	}

	public boolean changePassword(int id, String password) {
		Staff staff = staffDAO.getByKey(id);
		staff.setPassword(password);
		if (staffDAO.saveOrUpdate(staff))
			return true;
		else
			return false;
	}

	public int getParkingID(int staffId) {
		Staff staff = staffDAO.getByKey(staffId);
		return staff.getParkingLot().getId();
	}
	
	public List<Pending> getPendingTransaction() {
		PendingDAO pendingDAO = new PendingDAO();
		return pendingDAO.selectAll();
	}

	
	public boolean acceptPending(Pending pending) {
		PendingDAO pendingDAO = new PendingDAO();
		Std_TransactionDAO std_TransactionDAO = new Std_TransactionDAO();
		try {
			std_TransactionDAO.saveOrUpdate(new Std_Transaction(
				pending.getStudent(),
				pending.getAmount(),
				pending.getCreated_at(),
				PaymentMethod.TRANSFER
				));
			pendingDAO.delete(pending);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean discardPending(Pending pending) {
		PendingDAO pendingDAO = new PendingDAO();
		if (!pendingDAO.delete(pending)) return false;
		return true;
	}
}