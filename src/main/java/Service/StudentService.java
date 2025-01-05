package Service;

import Service.ReturnResult.TransactionDTO;
import dao.*;
import model.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudentService {
    private StudentDAO studentDAO = new StudentDAO();
    private Std_TransactionDAO std_TransactionDAO = new Std_TransactionDAO();
    private PendingDAO pendingDAO = new PendingDAO();
    private ParkingHistoryDAO parkingHistoryDAO = new ParkingHistoryDAO();

    public boolean checklogin(int username,String password) {
        if(studentDAO.getByKey(username).getStudent_id()!=null
                && studentDAO.getByKey(username).getPassword().equals(password)) return true;
        return false;
    }

//    public boolean signup(String mssv,String password) {
//        Student student = new Student();
//        student.setStudent_id(mssv);
//        student.setPassword(password);
//        return studentDAO.insert(student);
//
//    }

    public String getName(String studentId) {
        Student student = studentDAO.getByKey(studentId);
        return student.getFullname();
    }

    public boolean changePassword(String studentId, String password,String newPassword) {
        if(password.equals(newPassword)) {
            Student student = studentDAO.getByKey(studentId);
            student.setPassword(password);
            return studentDAO.saveOrUpdate(student);
        }else return false;

    }

    public boolean checkbalance(Student student, VehicleType vehicleType) {
        if(student.getBalance()>=vehicleType.getSession_fee()) return true;
        return false;
    }

    public int getBalance(String studentId) {
        Student student = studentDAO.getByKey(studentId);
        return student.getBalance();
    }

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

    public ParkingLot getCurrentPosition(String studentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ParkingHistory> query = session.createQuery("select parking_lot from ParkingHistory where student.id = :studentId and exit_time is null");
            return query.getSingleResult().getParking_lot();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
