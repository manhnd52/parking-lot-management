package Service;

import Service.ReturnResult.PaymentMethod;
import dao.PendingDAO;
import dao.StaffDAO;
import dao.Std_TransactionDAO;
import dao.StudentDAO;
import model.Pending;
import model.Staff;
import model.Std_Transaction;
import model.Student;

import java.util.List;

public class StaffService {
    private StaffDAO staffDAO=new StaffDAO();

    public boolean checklogin(int username,String password){
        if(staffDAO.getByKey(username)!=null
                &&staffDAO.getByKey(username).getPassword().equals(password)) return true;
        return false;
    }

    public boolean changePassword(int id,String password,String confirmPassword){
        Staff staff=staffDAO.getByKey(id);
        staff.setPassword(password);
        if(staffDAO.saveOrUpdate(staff)&&password.equals(confirmPassword)) return true;
        else return false;
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
        StudentDAO studentDAO = new StudentDAO();
        Std_TransactionDAO std_TransactionDAO = new Std_TransactionDAO();
        try {
            std_TransactionDAO.insert(new Std_Transaction(
                    pending.getStudent(),
                    pending.getAmount(),
                    pending.getCreated_at(),
                    PaymentMethod.TRANSFER
            ));
            Student student=pending.getStudent();
            student.setBalance(student.getBalance()+pending.getAmount());
            studentDAO.saveOrUpdate(student);
            pendingDAO.delete(pending);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean discardPending(Pending pending) {
        PendingDAO pendingDAO = new PendingDAO();
        if (!pendingDAO.delete(pending)) return false;
        return true;
    }

}
