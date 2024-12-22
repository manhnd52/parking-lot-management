package Service;

import dao.HibernateUtil;
import dao.PendingDAO;
import dao.Std_TransactionDAO;
import dao.StudentDAO;
import model.Pending;
import model.Std_Transaction;
import model.Student;
import model.VehicleType;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class StudentService {
    private StudentDAO studentDAO = new StudentDAO();
    private Std_TransactionDAO std_TransactionDAO = new Std_TransactionDAO();
    private PendingDAO pendingDAO = new PendingDAO();

    public int checklogin(int username,String password) {
        if(studentDAO.getByKey(username).getStudent_id()!=null
                && studentDAO.getByKey(username).getPassword().equals(password)) return 1;
        return 0;
    }

    public int checkbalance(Student student, VehicleType vehicleType) {
        if(student.getBalance()>=vehicleType.getSession_fee()) return 1;
        return 0;
    }

    public void requesttransaction(Student student,int amount) {
        Std_Transaction std_Transaction = new Std_Transaction(student,amount,false,
                new Timestamp(Calendar.getInstance().getTime().getTime()));
        std_TransactionDAO.insert(std_Transaction);
        Pending pending = new Pending(student,amount,
                new Timestamp(Calendar.getInstance().getTime().getTime()));
        pendingDAO.insert(pending);
    }

    public void responsetransaction(){
        for(Pending x:pendingDAO.selectAll()){
            Std_Transaction stdTransaction=std_TransactionDAO.getByKey(x.getId());
            stdTransaction.setType(Boolean.TRUE);
            Student student=studentDAO.getByKey(x.getStudent().getStudent_id());
            student.setBalance(student.getBalance()+stdTransaction.getAmount());
            if(studentDAO.saveOrUpdate(student)
                    &&std_TransactionDAO.saveOrUpdate(stdTransaction)
                    && pendingDAO.delete(x)){
                System.out.println("Nap tien thanh cong");
            }
        }
    }

    public List<Std_Transaction> sortTransactionDESC(){
        try(Session session= HibernateUtil.getSessionFactory().openSession()){
            Query<Std_Transaction> query=session.createQuery("FROM Std_Transaction " +
                    "order by created_at desc ");
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    public List<Std_Transaction> sortTransactionASC(){
        try(Session session= HibernateUtil.getSessionFactory().openSession()){
            Query<Std_Transaction> query=session.createQuery("FROM Std_Transaction " +
                    "order by created_at");
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }














}
